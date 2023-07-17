package com.github.xiaomiwujiecao.uniappmacrosupport.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

class ConditionalCompilationAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // 检查是否是注释
        if (element is PsiComment) {
            val text = element.text
            // 检查是否是条件编译标记
            if (text.contains("#ifdef", true) || text.contains("#ifndef", true) || text.contains("#endif", true)) {
                // 设置高亮显示的文本属性
                val attributes = TextAttributesKey.createTextAttributesKey("CONDITIONAL_COMPILATION")
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element)
                    .textAttributes(attributes)
                    .create()
            }
        }
    }
}
