Adapter 更新 :<br>
DiffCallback

圖片URL:<br>
class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadImage(url: String?) {
            url?.let {
                val urlText = it.replace("http:", "https:")
                this.load(urlText) {
                    placeholder(R.drawable.ic_download)
                    error(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }
}
