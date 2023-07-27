package com.github.xiaomiwujiecao.uniappmacrosupport.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class ConditionalCompilationFoldingBuilder : FoldingBuilderEx() {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
    val descriptors = mutableListOf<FoldingDescriptor>()
    val comments = PsiTreeUtil.findChildrenOfType(root, PsiComment::class.java)

    var startOffset = -1
    val ifdefStack = mutableListOf<PsiComment>()

    for (comment in comments) {
        val text = comment.text
        if (text.contains("#ifdef", true) || text.contains("#ifndef", true)) {
            startOffset = comment.textRange.startOffset
            ifdefStack.add(comment)
        } else if (text.contains("#endif")) {
            if (startOffset != -1 && ifdefStack.isNotEmpty()) {
                val endOffset = comment.textRange.endOffset
                val foldingRange = TextRange(startOffset, endOffset)
                descriptors.add(FoldingDescriptor(comment, foldingRange))
                startOffset = -1
                ifdefStack.removeLast()
            }
        }
    }

    return descriptors.toTypedArray()
}

    override fun getPlaceholderText(node: ASTNode): String {
        return "..."
    }

    override fun isCollapsedByDefault(p0: ASTNode): Boolean {
        return false
    }

    override fun getPlaceholderText(node: ASTNode, range: TextRange): String {
        return "..."
    }
}



