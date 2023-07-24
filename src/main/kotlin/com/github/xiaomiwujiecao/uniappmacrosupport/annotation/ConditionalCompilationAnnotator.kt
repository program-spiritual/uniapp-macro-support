package com.github.xiaomiwujiecao.uniappmacrosupport.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement


class ConditionalCompilationAnnotator : Annotator {
    val KEY: TextAttributesKey = createTextAttributesKey("SIMPLE_KEY", DefaultLanguageHighlighterColors.KEYWORD)
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // 检查是否是注释
        if (element is PsiComment) {
            val text = element.text
            // 检查是否是条件编译标记
            if (text.contains("#ifdef", true) || text.contains("#ifndef", true) || text.contains("#endif", true)) {
                holder.newSilentAnnotation(HighlightSeverity.TEXT_ATTRIBUTES)
                    .textAttributes(KEY)
                    .range(element)
                    .create()
            }
        }
    }
}
