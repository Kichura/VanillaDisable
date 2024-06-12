/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.gui;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uk.debb.vanilla_disable.config.data.DataDefinitions;
import uk.debb.vanilla_disable.config.data.SqlManager;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WorldgenConfigScreen extends Screen {
    private final CreateWorldScreen lastScreen;
    private Set<ResourceLocation> biomes;
    private Set<ResourceLocation> structures;
    private Set<ResourceLocation> placedFeatures;
    private String search = "";

    public WorldgenConfigScreen(CreateWorldScreen lastScreen) {
        super(Component.translatable("vd.worldgen_config"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        RegistryAccess.Frozen registryAccess = this.lastScreen.getUiState().getSettings().worldgenRegistries().compositeAccess();
        DataDefinitions.biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
        DataDefinitions.structureRegistry = registryAccess.registryOrThrow(Registries.STRUCTURE);
        DataDefinitions.placedFeatureRegistry = registryAccess.registryOrThrow(Registries.PLACED_FEATURE);
        biomes = DataDefinitions.biomeRegistry.keySet();
        structures = DataDefinitions.structureRegistry.keySet();
        placedFeatures = DataDefinitions.placedFeatureRegistry.keySet();
        WorldgenConfigList worldgenConfigList = new WorldgenConfigList();
        this.addRenderableWidget(worldgenConfigList);

        EditBox searchBox = new EditBox(this.font, this.width / 2 - 100, 40, 200, 20, Component.translatable("vd.worldgen_config.search"));
        searchBox.setValue(this.search);
        searchBox.setResponder(string -> {
            this.search = string;
            this.rebuildWidgets();
            this.setFocused(searchBox);
        });
        this.addRenderableWidget(searchBox);

        Button resetButton = Button.builder(Component.translatable("vd.worldgen_config.reset_all"), (button) -> {
            this.biomes.forEach(biome -> SqlManager.biomeMap.put(biome.toString(), true));
            this.structures.forEach(structure -> SqlManager.structureMap.put(structure.toString(), true));
            this.placedFeatures.forEach(placedFeature -> SqlManager.placedFeatureMap.put(placedFeature.toString(), true));
            SqlManager.placedFeatureMap.put("minecraft_unofficial:end_spike_cage", true);
            worldgenConfigList.refreshEntries();
        }).width(80).build();
        Button disableButton = Button.builder(Component.translatable("vd.worldgen_config.disable_all"), (button) -> {
            this.biomes.forEach(biome -> SqlManager.biomeMap.put(biome.toString(), false));
            this.structures.forEach(structure -> SqlManager.structureMap.put(structure.toString(), false));
            this.placedFeatures.forEach(placedFeature -> SqlManager.placedFeatureMap.put(placedFeature.toString(), false));
            SqlManager.placedFeatureMap.put("minecraft_unofficial:end_spike_cage", false);
            worldgenConfigList.refreshEntries();
        }).width(80).build();
        Button doneButton = Button.builder(Component.translatable("vd.worldgen_config.done"), (button) -> {
            this.onClose();
            worldgenConfigList.refreshEntries();
        }).width(80).build();

        resetButton.setPosition(this.width / 2 - 150, this.height - 25);
        disableButton.setPosition(this.width / 2 - 50, this.height - 25);
        doneButton.setPosition(this.width / 2 + 50, this.height - 25);
        this.addRenderableWidget(resetButton);
        this.addRenderableWidget(disableButton);
        this.addRenderableWidget(doneButton);
    }

    @Override
    public void render(@NotNull GuiGraphics g, int i, int j, float f) {
        super.render(g, i, j, f);
        g.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(this.minecraft).setScreen(this.lastScreen);
    }

    enum WorldgenConfigType {
        BIOME,
        STRUCTURE,
        PLACED_FEATURE
    }

    abstract static class WorldgenConfigEntry extends ContainerObjectSelectionList.Entry<WorldgenConfigScreen.WorldgenConfigEntry> {
        protected final List<AbstractWidget> children = Lists.newArrayList();
        protected final List<String> childrenNames = Lists.newArrayList();

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return this.children;
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return this.children;
        }
    }

    class WorldgenConfigCategoryEntry extends WorldgenConfigScreen.WorldgenConfigEntry {
        private final MutableComponent name;
        private final Button button;

        WorldgenConfigCategoryEntry(MutableComponent name, WorldgenConfigType type, WorldgenConfigList list) {
            this.name = name;
            this.button = Button.builder(Component.translatable("vd.worldgen_config.toggle_category"), button -> {
                switch (type) {
                    case BIOME -> {
                        boolean val = Collections.frequency(SqlManager.biomeMap.values(), false) < WorldgenConfigScreen.this.biomes.size() / 2;
                        WorldgenConfigScreen.this.biomes.forEach(biome -> SqlManager.biomeMap.put(biome.toString(), !val));
                    }
                    case STRUCTURE -> {
                        boolean val = Collections.frequency(SqlManager.structureMap.values(), false) < WorldgenConfigScreen.this.structures.size() / 2;
                        WorldgenConfigScreen.this.structures.forEach(structure -> SqlManager.structureMap.put(structure.toString(), !val));
                    }
                    case PLACED_FEATURE -> {
                        boolean val = Collections.frequency(SqlManager.placedFeatureMap.values(), false) < WorldgenConfigScreen.this.placedFeatures.size() / 2;
                        WorldgenConfigScreen.this.placedFeatures.forEach(placedFeature -> SqlManager.placedFeatureMap.put(placedFeature.toString(), !val));
                        SqlManager.placedFeatureMap.put("minecraft_unofficial:end_spike_cage", !val);
                    }
                }
                list.refreshEntries();
            }).width(100).build();
            this.children.add(this.button);
        }

        @Override
        public void render(@NotNull GuiGraphics g, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            g.drawString(WorldgenConfigScreen.this.font, this.name.withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN), k, j + 15, 16777215);
            this.button.setX(k + 125);
            this.button.setY(j + 5);
            this.button.render(g, i, j, f);
        }
    }

    class WorldgenConfigToggleEntry extends WorldgenConfigScreen.WorldgenConfigEntry {
        private final Checkbox checkbox;

        WorldgenConfigToggleEntry(String name, WorldgenConfigType type) {
            boolean value = SqlManager.biomeMap.getOrDefault(name, true) && SqlManager.structureMap.getOrDefault(name, true) && SqlManager.placedFeatureMap.getOrDefault(name, true);
            this.checkbox = Checkbox.builder(Component.literal(name), WorldgenConfigScreen.this.font).selected(value).onValueChange((checkbox, newVal) -> {
                switch (type) {
                    case BIOME -> SqlManager.biomeMap.put(name, newVal);
                    case STRUCTURE -> SqlManager.structureMap.put(name, newVal);
                    case PLACED_FEATURE -> SqlManager.placedFeatureMap.put(name, newVal);
                }
            }).build();
            this.children.add(this.checkbox);
            this.childrenNames.add(name);
        }

        @Override
        public void render(@NotNull GuiGraphics g, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            this.checkbox.setX(k);
            this.checkbox.setY(j + 10);
            this.checkbox.render(g, i, j, f);
        }
    }

    class WorldgenConfigList extends ContainerObjectSelectionList<WorldgenConfigScreen.WorldgenConfigEntry> {
        WorldgenConfigList() {
            super(Objects.requireNonNull(WorldgenConfigScreen.this.minecraft), WorldgenConfigScreen.this.width, WorldgenConfigScreen.this.height - 100, 68, 24);

            this.addEntry(new WorldgenConfigCategoryEntry(Component.translatable("vd.worldgen_config.category.biomes"), WorldgenConfigType.BIOME, this));
            WorldgenConfigScreen.this.biomes.stream().sorted().forEach(biome -> {
                if (ObjectList.of("minecraft:plains", "minecraft:the_nether", "minecraft:the_end").contains(biome.toString()))
                    return;
                if (biome.toString().contains(WorldgenConfigScreen.this.search)) {
                    this.addEntry(new WorldgenConfigToggleEntry(biome.toString(), WorldgenConfigType.BIOME));
                }
            });

            this.addEntry(new WorldgenConfigCategoryEntry(Component.translatable("vd.worldgen_config.category.structures"), WorldgenConfigType.STRUCTURE, this));
            WorldgenConfigScreen.this.structures.stream().sorted().forEach(structure -> {
                if (structure.toString().contains(WorldgenConfigScreen.this.search)) {
                    this.addEntry(new WorldgenConfigToggleEntry(structure.toString(), WorldgenConfigType.STRUCTURE));
                }
            });

            this.addEntry(new WorldgenConfigCategoryEntry(Component.translatable("vd.worldgen_config.category.placed_features"), WorldgenConfigType.PLACED_FEATURE, this));
            WorldgenConfigScreen.this.placedFeatures.stream().sorted().forEach(placedFeature -> {
                if (placedFeature.toString().contains(WorldgenConfigScreen.this.search)) {
                    this.addEntry(new WorldgenConfigToggleEntry(placedFeature.toString(), WorldgenConfigType.PLACED_FEATURE));
                }
            });
            if ("minecraft_unofficial:end_spike_cage".contains(WorldgenConfigScreen.this.search)) {
                this.addEntry(new WorldgenConfigToggleEntry("minecraft_unofficial:end_spike_cage", WorldgenConfigType.PLACED_FEATURE));
            }
        }

        public void refreshEntries() {
            this.children().forEach(child -> {
                if (child instanceof WorldgenConfigToggleEntry) {
                    int i = 0;
                    for (GuiEventListener c : child.children()) {
                        if (c instanceof Checkbox) {
                            String name = child.childrenNames.get(i);
                            ((Checkbox) c).selected = SqlManager.biomeMap.getOrDefault(name, true) && SqlManager.structureMap.getOrDefault(name, true) && SqlManager.placedFeatureMap.getOrDefault(name, true);
                            ++i;
                        }
                    }
                }
            });
        }
    }
}
