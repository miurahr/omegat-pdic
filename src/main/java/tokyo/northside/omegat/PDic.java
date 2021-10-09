package tokyo.northside.omegat;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.dictionaries.IDictionary;
import org.omegat.core.dictionaries.IDictionaryFactory;
import org.omegat.core.events.IApplicationEventListener;
import tokyo.northside.omegat.pdic.PdicDictionary;

import java.io.File;
import java.io.IOException;

/**
 * @author Hiroshi Miura
 */
public class PDic implements IDictionaryFactory {

    /**
     * Plugin loader.
     */
    public static void loadPlugins() {
        CoreEvents.registerApplicationEventListener(new PDicApplicationEventListener());
    }

    /**
     * Plugin unloader.
     */
    public static void unloadPlugins() {
    }

    /**
     * Determine whether or not the supplied file is supported by this factory.
     * This is intended to be a lightweight check, e.g. looking for a file
     * extension.
     *
     * @param file The file to check
     * @return Whether or not the file is supported
     */
    @Override
    public boolean isSupportedFile(final File file) {
        return file.getPath().endsWith(".DIC") || file.getPath().endsWith(".dic");
    }

    /**
     * Load the given file and return an {@link IDictionary} that wraps it.
     *
     * @param file The file to load
     * @return An IDictionary file that can read articles from the file
     */
    @Override
    public IDictionary loadDict(final File file) throws IOException {
        return new PdicDictionary(file);
    }

    static class PDicApplicationEventListener implements IApplicationEventListener {
        @Override
        public void onApplicationStartup() {
            Core.getDictionaries().addDictionaryFactory(new PDic());
        }

        @Override
        public void onApplicationShutdown() {
        }
    }
}
