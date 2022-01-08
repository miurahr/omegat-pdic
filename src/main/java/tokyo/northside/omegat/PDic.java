package tokyo.northside.omegat;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.dictionaries.IDictionary;
import org.omegat.core.dictionaries.IDictionaryFactory;
import org.omegat.core.events.IApplicationEventListener;

import java.io.File;
import java.io.IOException;

/**
 * @author Hiroshi Miura
 */
public final class PDic implements IDictionaryFactory {

    private static IApplicationEventListener listener;

    private PDic() {
    }

    /**
     * Plugin loader.
     */
    public static void loadPlugins() {
        listener = new PDicApplicationEventListener();
        CoreEvents.registerApplicationEventListener(listener);
    }

    /**
     * Plugin unloader.
     */
    public static void unloadPlugins() {
        CoreEvents.unregisterApplicationEventListener(listener);
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
        return new PdicDict(file);
    }

    /**
     * registration of dictionary factory.
     */
    static class PDicApplicationEventListener implements IApplicationEventListener {

        private IDictionaryFactory factory;

        @Override
        public void onApplicationStartup() {
            factory = new PDic();
            Core.getDictionaries().addDictionaryFactory(factory);
        }

        @Override
        public void onApplicationShutdown() {
            Core.getDictionaries().removeDictionaryFactory(factory);
        }
    }

}
