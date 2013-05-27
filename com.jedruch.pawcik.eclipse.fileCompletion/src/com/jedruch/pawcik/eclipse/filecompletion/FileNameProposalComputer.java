package com.jedruch.pawcik.eclipse.filecompletion;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

public class FileNameProposalComputer implements IJavaCompletionProposalComputer {

    public FileNameProposalComputer() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void sessionStarted() {
        System.out.println("sessions started");
        // TODO Auto-generated method stub

    }

    @Override
    public String getErrorMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sessionEnded() {
        System.out.println("sessions ended");
        // TODO Auto-generated method stub

    }

    @Override
    public List<ICompletionProposal> computeCompletionProposals(
            ContentAssistInvocationContext context, IProgressMonitor monitor) {
        IDocument document = context.getDocument();
        final int offset = context.getInvocationOffset();

        IRegion region;
        try {
            region = document.getLineInformationOfOffset(offset);
            final String content = document.get(region.getOffset(), region.getLength());

            int index = offset - region.getOffset() - 1;
            while (index >= 0 && !Character.isWhitespace(content.charAt(index)))
                index--;

            final int start = offset;
            final String candidate = content.substring(index + 1, offset - region.getOffset());
            File f = new File(candidate);
            if (f.isDirectory()) {
                return createP(Arrays.asList(f.list()), start);
            } else {
                File lastDif = new Path(candidate).removeLastSegments(1).toFile();
                if (lastDif.isDirectory()) {
                    return createP(filter(lastDif.list(), new Path(candidate).lastSegment()), start);
                }
            }
            System.out.println(candidate);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    private ArrayList<String> filter(String[] list, String lastSegment) {
        ArrayList<String> ret = new ArrayList<String>();
        for (String l : list) {
            if (l.startsWith(lastSegment))
                ret.add(l.substring(lastSegment.length()));
        }
        return ret;
    }

    /**
     * @param list
     * @param begining
     * @return
     */
    private List<ICompletionProposal> createP(Iterable<String> list, int begining) {
        List<ICompletionProposal> ret = new ArrayList<ICompletionProposal>();
        for (String f : list) {
            ret.add(new FilenameProposal(f, begining));
        }
        return ret;
    }

    @Override
    public List<IContextInformation> computeContextInformation(
            ContentAssistInvocationContext context, IProgressMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

}
