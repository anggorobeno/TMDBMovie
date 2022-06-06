package com.example.tmdbmovie.ui.customview

import android.content.Context
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import android.widget.TextView.BufferType.SPANNABLE
import com.example.tmdbmovie.R

object ResizableCustomView {
  fun doResizeTextView(
    context: Context, tv: TextView, maxLine: Int,
    expandText: String, viewMore: Boolean
  ) {
    if (tv.tag == null) {
      tv.tag = tv.text
    }
    val vto = tv.viewTreeObserver
    vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        val obs = tv.viewTreeObserver
        obs.removeGlobalOnLayoutListener(this)
        if (maxLine == 0) {
          val lineEndIndex = tv.layout.getLineEnd(0)
          val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
            .toString() + " "
          tv.text = text
          tv.movementMethod = LinkMovementMethod.getInstance()
          tv.setText(
            addClickablePartTextViewResizable(
              context, Html.fromHtml(
                tv.text.toString()
              ), tv, maxLine, expandText, viewMore
            ),
            SPANNABLE
          )
        } else if (maxLine > 0 && tv.lineCount >= maxLine) {
          val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
          val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
            .toString() + " " + expandText
          tv.text = text
          tv.movementMethod = LinkMovementMethod.getInstance()
          tv.setText(
            addClickablePartTextViewResizable(
              context,
              Html.fromHtml(tv.text.toString()), tv, maxLine, expandText,
              viewMore
            ), SPANNABLE
          )
        } else {
          // ISSUE ON DUTY: kadang2 tv.getlayout mengembalikan nilai null
          if (tv.layout != null) {
            val lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
            val text = tv.text.subSequence(0, lineEndIndex).toString() + ""
            tv.text = text
            tv.movementMethod = LinkMovementMethod.getInstance()
            tv.setText(
              addClickablePartTextViewResizable(
                context,
                Html.fromHtml(tv.text.toString()), tv, lineEndIndex,
                expandText, viewMore
              ), SPANNABLE
            )
          }
        }
      }
    })
  }

  private fun addClickablePartTextViewResizable(
    context: Context, strSpanned: Spanned, tv: TextView, maxLine: Int, spanableText: String,
    viewMore: Boolean
  ): SpannableStringBuilder {
    val str = strSpanned.toString()
    val ssb = SpannableStringBuilder(strSpanned)
    if (str.contains(spanableText)) {
      ssb.setSpan(
        object : ClickableSpan() {
          override fun onClick(widget: View) {
            if (viewMore) {
              tv.layoutParams = tv.layoutParams
              tv.setText(tv.tag.toString(), SPANNABLE)
              tv.invalidate()
              doResizeTextView(
                context, tv, -1,
                context.getString(R.string.all_less), false
              )
            } else {
              tv.layoutParams = tv.layoutParams
              tv.setText(tv.tag.toString(), SPANNABLE)
              tv.invalidate()
              doResizeTextView(
                context, tv, 3,
                context.getString(R.string.all_more), true
              )
            }
          }
        }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length,
        0
      )
    }
    return ssb
  }
}