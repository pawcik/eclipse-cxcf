/*
 * Copyright (c) 2013, Sabre Inc.
 */
package com.jedruch.pawcik.eclipse.filecompletion;

import java.io.File;

import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * @author Pawel Jedruch
 * 
 */
public class FilenameProposal implements IJavaCompletionProposal {

    private int offset;
    private String filename;

    public FilenameProposal(String f, int begining) {
        filename = f;
        offset = begining;
    }

    @Override
    public void apply(IDocument document) {
        try {
            document.replace(offset, 0, filename + File.separator);
        } catch (BadLocationException e) {
        }
    }

    @Override
    public Point getSelection(IDocument document) {
        return new Point(offset + filename.length() + 1, 0);
    }

    @Override
    public String getAdditionalProposalInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayString() {
        return filename;
    }

    @Override
    public Image getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextInformation getContextInformation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRelevance() {
        // TODO Auto-generated method stub
        return 0;
    }

}
