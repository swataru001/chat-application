package jp.co.rakus.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 名前とコメント内容と投稿Idを格納する.
 * @author wataru.saito
 *
 */
public class CommentForm {

	/**
	 * 投稿ID
	 */
	private String articleId;
	/**
	 * コメント主の名前
	 */
	@NotBlank(message="名前は必須です。")
	@Size(max=50, message="名前は50字以内で入力してください。")
	private String name;
	/**
	 * コメント内容
	 */
	@NotBlank(message="名前は必須です。")
	@Size(max=50, message="50字以内で入力してください。")
	private String content;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
