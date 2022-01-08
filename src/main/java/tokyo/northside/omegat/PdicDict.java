package tokyo.northside.omegat;

import io.github.eb4j.pdic.PdicDictionary;
import io.github.eb4j.pdic.PdicElement;
import org.omegat.core.dictionaries.DictionaryEntry;
import org.omegat.core.dictionaries.IDictionary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PDic access driver class.
 * @author Hiroshi Miura
 */
public final class PdicDict implements IDictionary {

    private final PdicDictionary dict;

    /**
     * Construct with .dic file.
     * It create index cache file with name .dic.idx.
     *
     * @param file PDIC .dic file.
     * @throws IOException when access error occurred.
     */
    public PdicDict(final File file) throws IOException {
        File cache = new File(file.getPath() + ".idx");
        this.dict = PdicDictionary.loadDictionary(file, cache);
    }

    /**
     * Read article's text.
     *
     * @param word The word to look up in the dictionary
     * @return List of entries. May be empty, but cannot be null.
     */
    @Override
    public List<DictionaryEntry> readArticles(final String word) {
        List<PdicElement> results = dict.getEntries(word);
        if (results == null) {
            return Collections.emptyList();
        }
        return makeDictionaryEntries(results);
    }

    /**
     * Read article's text. Matching is predictive, so e.g. supplying "term"
     * will return articles for "term", "terminology", "termite", etc.
     *
     * @param word The word to look up in the dictionary
     * @return List of entries. May be empty, but cannot be null.
     */
    @Override
    public List<DictionaryEntry> readArticlesPredictive(final String word) {
        List<PdicElement> results = dict.getEntriesPredictive(word);
        if (results == null) {
            return Collections.emptyList();
        }
        return makeDictionaryEntries(results);
    }

    /**
     * Dispose IDictionary. Default is no action.
     */
    @Override
    public void close() {
    }

    private List<DictionaryEntry> makeDictionaryEntries(final List<PdicElement> results) {
        List<DictionaryEntry> lists = new ArrayList<>();
        for (PdicElement result : results) {
            String disp = result.getDisp();
            if (disp.equals("")) {
                disp = result.getIndex();
            }
            StringBuilder sb = new StringBuilder();
            String phone = result.getPhone();
            if (phone != null) {
                sb.append(phone).append(" / ");
            }
            sb.append(result.getTrans()).append("<br/>");
            String sample = result.getSample();
            if (sample != null) {
                sb.append(sample);
            }
            lists.add(new DictionaryEntry(disp, sb.toString()));
        }
        return lists;
    }
}
