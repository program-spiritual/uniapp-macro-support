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
        for (comment in comments) {
            val text = comment.text
            if (text.contains("#ifdef", true) || text.contains("#ifndef", true)) {
                startOffset = comment.textRange.startOffset
            } else if (text.contains("#endif")) {
                if (startOffset != -1) {
                    val endOffset = comment.textRange.endOffset
                    val foldingRange = TextRange(startOffset, endOffset)
                    descriptors.add(FoldingDescriptor(comment, foldingRange))
                    startOffset = -1
                }
            }
        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String? {
        return "..."
    }

    override fun isCollapsedByDefault(p0: ASTNode): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPlaceholderText(node: ASTNode, range: TextRange): String? {
        return "..."
    }
}
