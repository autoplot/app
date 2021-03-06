/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.das2.jythoncompletion.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JToolTip;
import org.das2.jythoncompletion.support.CompletionDocumentation;
import org.das2.jythoncompletion.support.CompletionItem;
import org.das2.jythoncompletion.support.CompletionProvider;
import org.das2.jythoncompletion.support.CompletionResultSet;
import org.das2.jythoncompletion.support.CompletionTask;

/**
 *
 * @author Miloslav Metelka
 * @version 1.00
 */

public final class CompletionResultSetImpl {
    
    static {
        // Ensure the SPI accessor gets assigned
        try {
            Class.forName(CompletionResultSet.class.getName(), true, CompletionResultSetImpl.class.getClassLoader());
        } catch (ClassNotFoundException ex) {}
    }
    
    private static final CompletionSpiPackageAccessor spi
            = CompletionSpiPackageAccessor.get();

    private final CompletionImpl completionImpl;
    
    private final Object resultId;
    
    private final CompletionTask task;
    
    private final int queryType;
    
    private CompletionResultSet resultSet;
    
    private boolean active;

    private String title;
    
    private String waitText;
    
    private int anchorOffset;
    
    private List<CompletionItem> items;
    
    private boolean hasAdditionalItems;
    
    private boolean finished;
    
    private CompletionDocumentation documentation;
    
    private JToolTip toolTip;
    
    private int estimatedItemCount;
    
    private int estimatedItemWidth;
    
    CompletionResultSetImpl(CompletionImpl completionImpl,
    Object resultId, CompletionTask task, int queryType) {
        assert (completionImpl != null);
        assert (resultId != null);
        assert (task != null);
        this.completionImpl = completionImpl;
        this.resultId = resultId;
        this.task = task;
        this.queryType = queryType;
        this.anchorOffset = -1; // not set
        this.estimatedItemCount = -1; // not estimated
        this.active = true;
        
        spi.createCompletionResultSet(this);
    }
    
    /**
     * Get the result set instance associated with this implementation.
     */
    public synchronized CompletionResultSet getResultSet() {
        return resultSet;
    }
    
    public synchronized void setResultSet(CompletionResultSet resultSet) {
        assert (resultSet != null);
        assert (this.resultSet == null);
        this.resultSet = resultSet;
    }
    
    /**
     * Get the task associated with this result.
     */
    public CompletionTask getTask() {
        return task;
    }
    
    /**
     * Get the query type to which this result set belongs.
     * The results of other query types will be ignored when
     * being set into this result set.
     */
    public int getQueryType() {
        return queryType;
    }
    
    /**
     * Mark that results from this result set should no longer
     * be taken into account.
     */
    public synchronized void markInactive() {
        this.active = false;
    }

    public synchronized String getTitle() {
        return title;
    }

    public synchronized void setTitle(String title) {
        checkNotFinished();
        this.title = title;
    }
    
    public synchronized int getAnchorOffset() {
        return anchorOffset;
    }
    
    public synchronized void setAnchorOffset(int anchorOffset) {
        checkNotFinished();
        this.anchorOffset = anchorOffset;
    }
    
    public synchronized boolean addItem(CompletionItem item) {
        assert (item != null) : "Added item cannot be null";
        checkNotFinished();
        if (!active || (queryType & CompletionProvider.COMPLETION_QUERY_TYPE) == 0) {
            return false; // signal do not add any further results
        }

        if (items == null) {
            int estSize = (estimatedItemCount == -1) ? 10 : estimatedItemCount;
            items = new ArrayList<CompletionItem>(estSize);
        }
        items.add(item);
        return items.size() < 1000;
    }
    
    public boolean addAllItems(Collection<? extends CompletionItem> items) {
        boolean cont = true;
        for (Iterator<? extends CompletionItem> it = items.iterator(); it.hasNext();) {
            cont = addItem(it.next());
        }
        return cont;
    }
    
    /**
     * @return non-null list of items.
     */
    public synchronized List<? extends CompletionItem> getItems() {
        assert isFinished() : "Adding not finished";
        return (items != null) ? items : Collections.<CompletionItem>emptyList();
    }
    
    
    public synchronized void setHasAdditionalItems(boolean value) {
        checkNotFinished();
        if (queryType != CompletionProvider.COMPLETION_QUERY_TYPE) {
            return;
        }
        this.hasAdditionalItems = value;
    }

    public synchronized boolean hasAdditionalItems() {
        return hasAdditionalItems;
    }    
    
    public synchronized void setDocumentation(CompletionDocumentation documentation) {
        checkNotFinished();
        if (!active || queryType != CompletionProvider.DOCUMENTATION_QUERY_TYPE) {
            return;
        }
        this.documentation = documentation;
    }
    
    public synchronized CompletionDocumentation getDocumentation() {
        return documentation;
    }
    
    public synchronized JToolTip getToolTip() {
        return toolTip;
    }
    
    public synchronized void setToolTip(JToolTip toolTip) {
        checkNotFinished();
        if (!active || queryType != CompletionProvider.TOOLTIP_QUERY_TYPE) {
            return;
        }
        this.toolTip = toolTip;
    }
    
    public synchronized boolean isFinished() {
        return finished;
    }
    
    public void finish() {
        synchronized (this) {
            if (finished) {
                throw new IllegalStateException("finish() already called"); // NOI18N
            }
            finished = true;
        }

        completionImpl.finishNotify(this);
    }
    
    public int getSortType() {
        return completionImpl.getSortType();
    }
    
    public synchronized void estimateItems(int estimatedItemCount, int estimatedItemWidth) {
        this.estimatedItemCount = estimatedItemCount;
        this.estimatedItemWidth = estimatedItemWidth;
    }
    
    CompletionImpl getCompletionImpl() {
        return completionImpl;
    }
    
    Object getResultId() {
        return resultId;
    }
    
    private void checkNotFinished() {
        if (isFinished()) {
            throw new IllegalStateException("Result set already finished"); // NOI18N
        }
    }

    public synchronized String getWaitText() {
        return waitText;
    }

    public synchronized void setWaitText(String waitText) {
        this.waitText = waitText;
    }

}
