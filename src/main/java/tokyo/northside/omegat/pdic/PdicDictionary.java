package tokyo.northside.omegat.pdic;

import org.omegat.core.dictionaries.DictionaryEntry;
import org.omegat.core.dictionaries.IDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wak (Apache-2.0)
 * @author Hiroshi Miura
 */
public class PdicDictionary implements IDictionary {

    static final Logger LOG = LoggerFactory.getLogger(PdicDictionary.class.getName());

    private PdicInfo dicInfo;

    /**
     * Constructor of dictionary driver.
     * @param file DIC file.
     * @throws IOException when got error.
     */
    public PdicDictionary(final File file) throws IOException {
        String cachePath = file.getPath() + ".idx";
        final int headerSize = 256;
        PdicHeader header; // ヘッダー

        ByteBuffer headerbuff = ByteBuffer.allocate(headerSize);
        try (FileInputStream srcStream = new FileInputStream(file);
             FileChannel srcChannel = srcStream.getChannel()) {
            int len = srcChannel.read(headerbuff);
            srcChannel.close();
            if (len == headerSize) {
                header = new PdicHeader();
                if (header.load(headerbuff) != 0) {
                    // Unicode辞書 かつ ver5以上のみ許容
                    if ((header.version & 0xFF00) < 0x0500 || header.os != 0x20) {
                        LOG.warn("Unsupported dictionary version" + file.getName());
                        throw new RuntimeException();
                    }
                    dicInfo = new PdicInfo(file, header.header_size + header.extheader,
                            header.block_size * header.index_block, header.nindex2, header.index_blkbit,
                            header.block_size);
                    if (!dicInfo.readIndexBlock(cachePath)) {
                        LOG.warn("Failed to load dictionary index of " + file.getName());
                        throw new RuntimeException();
                    }
                    dicInfo.setDicName(file.getName());
                }
            }
        }
    }

    /**
     * Read article's text.
     *
     * @param word The word to look up in the dictionary
     * @return List of entries. May be empty, but cannot be null.
     */
    @Override
    public List<DictionaryEntry> readArticles(final String word) {
        List<DictionaryEntry> lists = new ArrayList<>();
        if (dicInfo.searchWord(word.toLowerCase())) {
            PdicResult result = dicInfo.getResult();
            for (int i = 0; i < result.getCount(); i++) {
                String disp = result.getDisp(i);
                if (disp.equals("")) {
                    disp = result.getIndex(i);
                }
                StringBuilder sb = new StringBuilder();
                String phone = result.getPhone(i);
                if (phone != null) {
                    sb.append(phone).append(" / ");
                }
                sb.append(result.getTrans(i)).append("<br/>");
                String sample = result.getSample(i);
                if (sample != null) {
                    sb.append(sample);
                }
                lists.add(new DictionaryEntry(disp, sb.toString()));
            }
        }
        return lists;
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
        return readArticles(word);
    }

    /**
     * Dispose IDictionary. Default is no action.
     */
    @Override
    public void close() {
    }
}
