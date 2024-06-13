/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package uk.debb.vanilla_disable.config.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.tabs.GridLayoutTab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
    private final TabManager tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private TabNavigationBar tabNavigationBar;
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

        tabNavigationBar = TabNavigationBar.builder(this.tabManager, this.width)
                .addTabs(
                        new Tab("biomes", DataDefinitions.biomeRegistry.keySet(), Type.BIOMES),
                        new Tab("placed_features", DataDefinitions.placedFeatureRegistry.keySet(), Type.PLACED_FEATURES),
                        new Tab("structures", DataDefinitions.structureRegistry.keySet(), Type.STRUCTURES)
                ).build();
        this.addRenderableWidget(tabNavigationBar);
        LinearLayout linearLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout.addChild(Button.builder(Component.translatable("vd.config.cancel"), button -> {
            SqlManager.worldgenMaps.forEach((table, map) -> map.clear());
            onClose();
        }).build());
        linearLayout.addChild(Button.builder(Component.translatable("vd.config.done"), button -> onClose()).build());
        this.layout.visitWidgets(abstractWidget -> {
            abstractWidget.setTabOrderGroup(1);
            this.addRenderableWidget(abstractWidget);
        });
        tabNavigationBar.selectTab(0, false);
        repositionElements();
    }

    @Override
    public void repositionElements() {
        if (this.tabNavigationBar != null) {
            this.tabNavigationBar.setWidth(this.width);
            this.tabNavigationBar.arrangeElements();
            int i = this.tabNavigationBar.getRectangle().bottom();
            ScreenRectangle screenRectangle = new ScreenRectangle(0, i, this.width, this.height - this.layout.getFooterHeight() - i);
            this.tabManager.setTabArea(screenRectangle);
            this.layout.setHeaderHeight(i);
            this.layout.arrangeElements();
        }
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(minecraft).setScreen(lastScreen);
    }

    enum Type {
        BIOMES,
        PLACED_FEATURES,
        STRUCTURES
    }

    abstract static class WorldgenListEntry extends ContainerObjectSelectionList.Entry<WorldgenListEntry> {
        protected final List<AbstractWidget> children = Lists.newArrayList();
        protected final List<String> childrenNames = Lists.newArrayList();

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return this.children;
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return this.children;
        }
    }

    class Tab extends GridLayoutTab {

        Tab(String tabName, Set<ResourceLocation> data, Type type) {
            super(Component.translatable("vd.worldgen_config.tab." + tabName));
            GridLayout.RowHelper rowHelper = this.layout.rowSpacing(8).createRowHelper(1);
            rowHelper.addChild(new WorldgenList(data, type));
        }
    }

    class WorldgenListHeaderEntry extends WorldgenListEntry {
        private final EditBox searchBox;
        private final Button toggleButton;

        WorldgenListHeaderEntry(WorldgenList list, Set<ResourceLocation> data, Type type) {
            searchBox = new EditBox(WorldgenConfigScreen.this.font, 0, 25, (int) (width * 0.35) - 67, 20, Component.translatable("vd.config.search"));
            searchBox.setValue(WorldgenConfigScreen.this.search);
            searchBox.setResponder(string -> {
                WorldgenConfigScreen.this.search = string;
                list.populate(true);
            });
            toggleButton = Button.builder(Component.translatable("vd.worldgen_config.toggle_all"), button -> {
                boolean val = Collections.frequency(SqlManager.worldgenMaps.get(type.name().toLowerCase()).values(), false) < data.size() / 2;
                data.forEach(entry -> SqlManager.worldgenMaps.get(type.name().toLowerCase()).put(entry.toString(), !val));
                if (type == Type.PLACED_FEATURES) {
                    SqlManager.worldgenMaps.get("placed_features").put("minecraft_unofficial:end_spike_cage", !val);
                }
                list.refreshEntries();
            }).width(65).build();
            this.children.add(searchBox);
            this.children.add(toggleButton);
        }

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            this.searchBox.setX(left);
            this.searchBox.setY(top);
            this.searchBox.render(guiGraphics, index, top, partialTick);
            this.toggleButton.setX(left + width - 58);
            this.toggleButton.setY(top);
            this.toggleButton.render(guiGraphics, index, top, partialTick);
        }
    }

    class WorldgenListToggleEntry extends WorldgenListEntry {
        private final Checkbox checkbox;

        WorldgenListToggleEntry(String name, Type type) {
            boolean value = SqlManager.worldgenMaps.values().stream().allMatch(map -> map.getOrDefault(name, true));
            this.checkbox = Checkbox.builder(Component.literal(name), WorldgenConfigScreen.this.font).selected(value).onValueChange((checkbox, newVal) ->
                    SqlManager.worldgenMaps.get(type.name().toLowerCase()).put(name, newVal)).build();
            this.children.add(this.checkbox);
            this.childrenNames.add(name);
        }

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            this.checkbox.setX(left);
            this.checkbox.setY(top);
            this.checkbox.render(guiGraphics, index, top, partialTick);
        }
    }

    class WorldgenList extends ContainerObjectSelectionList<WorldgenListEntry> {
        private final Set<ResourceLocation> entries;
        private final Type type;

        WorldgenList(Set<ResourceLocation> entries, Type type) {
            super(Objects.requireNonNull(WorldgenConfigScreen.this.minecraft), WorldgenConfigScreen.this.width, WorldgenConfigScreen.this.height - WorldgenConfigScreen.this.layout.getFooterHeight() * 2 + 8, 75, 24);
            this.entries = entries;
            this.type = type;
            populate(false);
        }

        public void populate(boolean repopulate) {
            if (repopulate) {
                WorldgenListEntry header = this.children().getFirst();
                this.children().clear();
                this.children().add(header);
            } else {
                this.addEntry(new WorldgenListHeaderEntry(this, this.entries, this.type));
            }
            this.entries.stream().sorted().forEach(entry -> {
                if ("minecraft:plains minecraft:nether_wastes minecraft:the_end".contains(entry.toString())) return;
                if (!entry.toString().contains(WorldgenConfigScreen.this.search)) return;
                this.addEntry(new WorldgenListToggleEntry(entry.toString(), this.type));
            });
            if (this.type == Type.PLACED_FEATURES && "minecraft_unofficial:end_spike_cage".contains(WorldgenConfigScreen.this.search)) {
                this.addEntry(new WorldgenListToggleEntry("minecraft_unofficial:end_spike_cage", this.type));
            }
        }

        public void refreshEntries() {
            this.children().forEach(child -> {
                if (child instanceof WorldgenListToggleEntry) {
                    int i = 0;
                    for (GuiEventListener c : child.children()) {
                        if (c instanceof Checkbox) {
                            String name = child.childrenNames.get(i);
                            ((Checkbox) c).selected = SqlManager.worldgenMaps.values().stream().allMatch(map -> map.getOrDefault(name, true));
                            ++i;
                        }
                    }
                }
            });
        }
    }
}
