package tokyo.northside.omegat.pdic;

import java.util.ArrayList;

/**
 * @author wak (Apache-2.0)
 * @author Hiroshi Miura
 */
final class PdicResult extends ArrayList<PdicElement> {

    private static final long serialVersionUID = -7784622190169021306L;

    public int getCount() {
        return size();
    }

    public String getIndex(final int idx) {
        return get(idx).mIndex;
    }

    public String getDisp(final int idx) {
        return get(idx).mDisp;
    }

    public byte getAttr(final int idx) {
        return get(idx).mAttr;
    }

    public String getTrans(final int idx) {
        return get(idx).mTrans;
    }

    public String getPhone(final int idx) {
        return get(idx).mPhone;
    }

    public String getSample(final int idx) {
        return get(idx).mSample;
    }

}