package icu.bughub.app.todo.actions

sealed class EditorAction {
    //获取数据
    data class FetchDetail(val id: String) : EditorAction()

    //文本框输入数据
    data class OnTextChanged(val content: String) : EditorAction()

    //保存数据
    object Save : EditorAction()
}
