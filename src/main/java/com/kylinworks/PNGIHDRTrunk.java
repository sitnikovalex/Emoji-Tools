/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kylinworks;

/**
 * @author Rex
 */
class PNGIHDRTrunk extends PNGTrunk {
    public final int m_nWidth;
    public final int m_nHeight;

    public PNGIHDRTrunk(int nSize, String szName, byte[] nData, byte[] nCRC) {
        super(nSize, szName, nData, nCRC);

        m_nWidth = readInt(nData, 0);
        m_nHeight = readInt(nData, 4);
    }
}
