import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.FabricProject;
import io.github.coolcrabs.brachyura.mappings.Namespaces;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import io.github.coolcrabs.brachyura.processing.ProcessorChain;
import net.fabricmc.accesswidener.AccessWidenerReader;
import net.fabricmc.accesswidener.AccessWidenerVisitor;
import net.fabricmc.mappingio.tree.MappingTree;

public class Buildscript extends FabricProject {
    @Override
    public VersionMeta createMcVersion() {
        // Minecraft Version
        return Minecraft.getVersion("1.18.2");
    }

    @Override
    public MappingTree createMappings() {
        // Uses Mojang Official Mappings
        return createMojmap();
    }

    @Override
    public FabricLoader getLoader() {
        // Fabric Loader Version
        return new FabricLoader(FabricMaven.URL, FabricMaven.loader("0.13.3"));
    }

    @Override
    public String getModId() {
        // Mod Name
        return "vanilla_disable";
    }

    @Override
    public String getVersion() {
        // Mod Version
        return "1.5.1";
    }

    @Override
    public Consumer<AccessWidenerVisitor> getAw() {
        // The path to the accesswidener
        return v -> {
            try {
                new AccessWidenerReader(v).read(Files.newBufferedReader(getResourcesDir().resolve("vanilla_disable.aw")), Namespaces.NAMED);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // Fabric API
        d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-resource-loader-v0", "0.4.16+55dca1a4d2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
        d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-game-rule-api-v1", "1.0.12+d7c144a8d2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
        d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-lifecycle-events-v1", "2.0.1+25407454d2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
        d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api-base", "0.4.3+d7c144a8d2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
        // CaffeineConfig
        d.addMaven("https://jitpack.io", new MavenId("com.github.FlashyReese:CaffeineConfig:383ee33be5"), ModDependencyFlag.COMPILE, ModDependencyFlag.JIJ, ModDependencyFlag.RUNTIME);
        // LazyDFU
        d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth:lazydfu:0.1.2"), ModDependencyFlag.RUNTIME);
        // Tiefix
        d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth:tiefix:1.9.0"), ModDependencyFlag.RUNTIME);
    }

    @Override
    public int getJavaVersion() {
        // Default is Java 8
        return 17;
    }

    @Override
    public BrachyuraDecompiler decompiler() {
        // Uses QuiltFlower instead of CFR
        return new FernflowerDecompiler(Maven.getMavenJarDep("https://maven.quiltmc.org/repository/release", new MavenId("org.quiltmc:quiltflower:1.7.0"))); 
    };

    @Override
    public Path getBuildJarPath() {
        // Changes the jar file name
        return getBuildLibsDir().resolve(getModId() + "-" + "mc" + createMcVersion().version + "-" + getVersion() + ".jar");
    }

    @Override
    public ProcessorChain resourcesProcessingChain() {
        // Overrides the version tag in fabric.mod.json
        return new ProcessorChain(super.resourcesProcessingChain(), new FmjVersionFixer(this));
    }
}
