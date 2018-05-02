package com.lsy.laterbook.ui.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.bestxty.ai.domain.bean.Record;
import com.chuangfeigu.tools.app.T;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookPageFactory implements BookPageFactoryInterface {

    private File book_file = null;
    private MappedByteBuffer m_mbBuf = null;
    private int m_mbBufLen = 0;
    private int m_mbBufBegin = 0;
    private int m_mbBufEnd = 0;
    private String m_strCharsetName = "GBK";
    private Bitmap m_book_bg = null;
    public int mWidth;
    public int mHeight;

    private List<String> m_lines = new ArrayList<>();

    private int m_fontSize = 52;
    private int m_textColor = Color.BLACK;
    private int m_backColor = 0xff888888; // 背景颜色
    private int marginWidth = 52; // 左右与边缘的距离
    private int marginHeight = 52; // 上下与边缘的距离
    public int mLineCount; // 每页可以显示的行数
    private float mVisibleHeight; // 绘制内容的宽  
    private float mVisibleWidth; // 绘制内容的宽  
    private boolean m_isfirstPage, m_islastPage;
    public Callx callx;
    private Canvas mc;
    private boolean isnight;

    // private int m_nLineSpaceing = 5;
    public static interface Callx {
        void dosomething();
    }

    public Paint mPaint;

    public BookPageFactory(int w, int h, Callx callx) {
        this.callx = callx; // TODO Auto-generated constructor stub
        mWidth = w;
        mHeight = h;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Align.LEFT);//设置绘制文字的对齐方向    
        mPaint.setTextSize(m_fontSize);
        mPaint.setColor(m_textColor);
        mVisibleWidth = mWidth - marginWidth * 2;
        mVisibleHeight = mHeight - marginHeight * 2;
        mLineCount = (int) (mVisibleHeight / m_fontSize); // 可显示的行数  
    }

    public void init(Record strFilePath) {
        book_file = new File(strFilePath.getSourceId());
        long lLen = book_file.length();
        m_mbBufLen = (int) lLen;

        /*
         * 内存映射文件能让你创建和修改那些因为太大而无法放入内存的文件。有了内存映射文件，你就可以认为文件已经全部读进了内存，
         * 然后把它当成一个非常大的数组来访问。这种解决办法能大大简化修改文件的代码。
         *
        * fileChannel.map(FileChannel.MapMode mode, long position, long size)将此通道的文件区域直接映射到内存中。但是，你必
        * 须指明，它是从文件的哪个位置开始映射的，映射的范围又有多大
        */
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(book_file, "r").getChannel();
            //文件通道的可读可写要建立在文件流本身可读写的基础之上
            m_mbBuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, lLen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m_mbBufBegin = strFilePath.getReadIndex();
        m_mbBufEnd = m_mbBufBegin;
    }

    @Override
    public int getReadindex() {
        return m_mbBufBegin;
    }

    @Override
    public void setIndex(int index) {
        if (index > -1 && index < m_mbBufLen) {
            m_mbBufBegin = index;
            m_mbBufEnd = index;
            onDraw(mc);
            callx.dosomething();
        } else {
            T.showToast("错误的位置");
        }
    }

    @Override
    public void setNight(boolean isnight) {
        this.isnight = isnight;
        if (isnight)
            mPaint.setColor(Color.WHITE);
        else {
            mPaint.setColor(Color.BLACK);
        }
        callx.dosomething();
    }

    @Override
    public void setmNextPageCanvas(Canvas mCurPageCanvas) {
        this.mc = mCurPageCanvas;
    }

    @Override
    public void destory() {
        m_mbBuf.clear();
        m_mbBuf = null;
    }

    @Override
    public int getTotal() {
        return m_mbBufLen;
    }

    @Override
    public int current() {
        return m_mbBufBegin;
    }


    protected byte[] readParagraphBack(int nFromPos) {
        int nEnd = nFromPos;
        int i;
        byte b0, b1;
        if (m_strCharsetName.equals("UTF-16LE")) {
            i = nEnd - 2;
            while (i > 0) {
                b0 = m_mbBuf.get(i);
                b1 = m_mbBuf.get(i + 1);
                if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
                    i += 2;
                    break;
                }
                i--;
            }

        } else if (m_strCharsetName.equals("UTF-16BE")) {
            i = nEnd - 2;
            while (i > 0) {
                b0 = m_mbBuf.get(i);
                b1 = m_mbBuf.get(i + 1);
                if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
                    i += 2;
                    break;
                }
                i--;
            }
        } else {
            i = nEnd - 1;
            while (i > 0) {
                b0 = m_mbBuf.get(i);
                if (b0 == 0x0a && i != nEnd - 1) {
                    i++;
                    break;
                }
                i--;
            }
        }
        if (i < 0)
            i = 0;
        int nParaSize = nEnd - i;
        int j;
        byte[] buf = new byte[nParaSize];
        for (j = 0; j < nParaSize; j++) {
            buf[j] = m_mbBuf.get(i + j);
        }
        return buf;
    }


    //读取上一段落
    protected byte[] readParagraphForward(int nFromPos) {
        int nStart = nFromPos;
        int i = nStart;
        byte b0, b1;
        // 根据编码格式判断换行  
        if (m_strCharsetName.equals("UTF-16LE")) {
            while (i < m_mbBufLen - 1) {
                b0 = m_mbBuf.get(i++);
                b1 = m_mbBuf.get(i++);
                if (b0 == 0x0a && b1 == 0x00) {
                    break;
                }
            }
        } else if (m_strCharsetName.equals("UTF-16BE")) {
            while (i < m_mbBufLen - 1) {
                b0 = m_mbBuf.get(i++);
                b1 = m_mbBuf.get(i++);
                if (b0 == 0x00 && b1 == 0x0a) {
                    break;
                }
            }
        } else {
            while (i < m_mbBufLen) {
                b0 = m_mbBuf.get(i++);
                if (b0 == 0x0a) {
                    break;
                }
            }
        }
        //共读取了多少字符  
        int nParaSize = i - nStart;
        byte[] buf = new byte[nParaSize];
        for (i = 0; i < nParaSize; i++) {
            //将已读取的字符放入数组  
            buf[i] = m_mbBuf.get(nFromPos + i);
        }
        return buf;
    }

    protected List<String> pageDown() {
        String strParagraph = "";
        List<String> lines = new ArrayList<>();
        while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
            byte[] paraBuf = readParagraphForward(m_mbBufEnd); // 读取一个段落
            m_mbBufEnd += paraBuf.length;//结束位置后移paraBuf.length  
            try {
                strParagraph = new String(paraBuf, m_strCharsetName);//通过decode指定的编码格式将byte[]转换为字符串           
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            String strReturn = "";

            //去除将字符串中的特殊字符  
            if (strParagraph.indexOf("\r\n") != -1) {
                strReturn = "\r\n";
                strParagraph = strParagraph.replaceAll("\r\n", "");
            } else if (strParagraph.indexOf("\n") != -1) {
                strReturn = "\n";
                strParagraph = strParagraph.replaceAll("\n", "");
            }

            if (strParagraph.length() == 0) {
                lines.add(strParagraph);
            }
            while (strParagraph.length() > 0) {
                //计算每行可以显示多少个字符  
                //获益匪浅  
                int nSize = gettheli(strParagraph);
                lines.add(strParagraph.substring(0, nSize));
                strParagraph = strParagraph.substring(nSize);//截取从nSize开始的字符串  
                if (lines.size() >= mLineCount) {
                    break;
                }
            }
            //当前页没显示完  
            if (strParagraph.length() != 0) {
                try {
                    m_mbBufEnd -= (strParagraph + strReturn)
                            .getBytes(m_strCharsetName).length;
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block  
                    e.printStackTrace();
                }
            }
        }
        return lines;
    }

    public int gettheli(String strParagraph) {
        if (strParagraph != null) {
            return mPaint.breakText(strParagraph, true, mVisibleWidth, null);
        }
        return 0;
    }

    protected void pageUp() {
        if (m_mbBufBegin < 0)
            m_mbBufBegin = 0;
        List<String> lines = new ArrayList<>();
        String strParagraph = "";
        while (lines.size() < mLineCount && m_mbBufBegin > 0) {
            List<String> paraLines = new ArrayList<>();
            byte[] paraBuf = readParagraphBack(m_mbBufBegin);
            m_mbBufBegin -= paraBuf.length;
            try {
                strParagraph = new String(paraBuf, m_strCharsetName);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            strParagraph = strParagraph.replaceAll("\r\n", "");
            strParagraph = strParagraph.replaceAll("\n", "");

            if (strParagraph.length() == 0) {
                paraLines.add(strParagraph);
            }
            while (strParagraph.length() > 0) {
                int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
                        null);
                paraLines.add(strParagraph.substring(0, nSize));
                strParagraph = strParagraph.substring(nSize);
            }
            lines.addAll(0, paraLines);
        }
        while (lines.size() > mLineCount) {
            try {
                m_mbBufBegin += lines.get(0).getBytes(m_strCharsetName).length;
                lines.remove(0);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
        m_mbBufEnd = m_mbBufBegin;
        return;
    }

    public void prePage() {
        if (m_mbBufBegin <= 0) {
            //第一页  
            m_mbBufBegin = 0;
            m_isfirstPage = true;
            return;
        } else m_isfirstPage = false;
        m_lines.clear();//Removes all elements from this List, leaving it empty.  
        pageUp();
        m_lines = pageDown();
    }

    public void nextPage() {
        if (m_mbBufEnd >= m_mbBufLen) {
            m_islastPage = true;
            return;
        } else m_islastPage = false;
        m_lines.clear();
        m_mbBufBegin = m_mbBufEnd;
        m_lines = pageDown();
    }

    public void drawText(List<String> m_lines, Canvas c) {

        if (m_lines.size() > 0) {
            if (m_book_bg == null || isnight)
                c.drawColor(m_backColor);
            else
                c.drawBitmap(m_book_bg, new Rect(0, 0, m_book_bg.getWidth(), m_book_bg.getHeight()), new Rect(0, 0, mWidth, mHeight), null);
            int y = marginHeight + 22;
            for (String strLine : m_lines) {
                y += m_fontSize;
                //从（x,y）坐标将文字绘于手机屏幕
                c.drawText(strLine, marginWidth, y, mPaint);
            }
        }
    }

    public void onDraw(Canvas c) {
        if (m_lines.size() == 0)
            m_lines = pageDown();
        drawText(m_lines, c);
        //计算百分比（不包括当前页）并格式化  
        float fPercent = (float) (m_mbBufBegin * 1.0 / m_mbBufLen);
        DecimalFormat df = new DecimalFormat("#0.0");
        String strPercent = df.format(fPercent * 100) + "%";

        //计算999.9%所占的像素宽度     
        int nPercentWidth = (int) mPaint.measureText("999.9%") + 1;
        c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 5, mPaint);
    }

    public void setBgBitmap(Bitmap BG) {
        m_book_bg = BG;
    }

    public boolean isfirstPage() {
        return m_isfirstPage;
    }

    public boolean islastPage() {
        return m_islastPage;
    }
}  