package com.github.xiaomiwujiecao.uniappmacrosupport.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
class IfdefAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is PsiFile) {
            val visitor = IfdefVisitor(holder)
            element.accept(visitor)
        }
    }
}
class IfdefVisitor(private val holder: AnnotationHolder) : PsiRecursiveElementVisitor() {
    override fun visitElement(element: PsiElement) {
        if (element.textMatches("ifdef") || element.textMatches("endif")) {
            val attributes = TextAttributesKey.createTextAttributesKey("IFDEF_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
            holder.createInfoAnnotation(element, null).textAttributes = attributes
        }
        super.visitElement(element)
    }
}
