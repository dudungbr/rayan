package com.siscomercio.utilities;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Rayan
 */
public class UpperCaseLetter extends PlainDocument
{
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param offs
     * @param str
     * @param a
     * <p/>
     * @throws BadLocationException
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
        super.insertString(offs, str.toUpperCase(), a);
    }
}
