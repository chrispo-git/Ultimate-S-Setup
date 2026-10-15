package com.example.ultssetup;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderSetup
{
    private FolderSetup()
    {
    }
    
    public static void setup(String pathRoot, Consumer<String> onProgress) throws IOException
    {
        if (pathRoot.endsWith("/")) pathRoot = pathRoot.substring(0,pathRoot.length()-1);
        Path skyline = Paths.get(pathRoot+"/atmosphere/contents/01006A800016E000/romfs/skyline");
        Path plugins = skyline.resolve("plugins");
        Path disabledPlugins = skyline.resolve("disabled_plugins");
        Files.createDirectories(disabledPlugins);

        String[] conflictingPlugins = new String[]{
            "libtraining_modpack.nro",
            "libparam_hook.nro",
            "libacmd_hook.nro",
            "libsmashline_hook.nro"
        };
        for (String plugin : conflictingPlugins) {
            if (Files.isRegularFile(plugins.resolve(plugin))) {
                onProgress.accept("Disabling " + plugin);
                Files.move(plugins.resolve(plugin), disabledPlugins.resolve(plugin), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        CopyDir.copyDirectory(Paths.get("atmosphere/"), Paths.get(pathRoot+"/atmosphere/"), file -> onProgress.accept("Copying " + file));
        String[] oldInstalls = new String[]{
            "/ultimate/ult-s",
            "/ultimate/mods/Ultimate S Arcropolis",
            "/ultimate/mods/Ultimate S Stages"
        };
        for (String dir : oldInstalls) {
            onProgress.accept("Removing old " + dir.substring(1));
            deleteContents(Paths.get(pathRoot + dir));
        }
        CopyDir.copyDirectory(Paths.get("ultimate/"), Paths.get(pathRoot+"/ultimate/"), file -> onProgress.accept("Copying " + file));
    }

    private static void deleteContents(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) return;
        List<Path> paths;
        try (Stream<Path> walk = Files.walk(dir)) {
            paths = walk.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        for (Path p : paths) {
            if (!p.equals(dir)) Files.delete(p);
        }
    }
}
