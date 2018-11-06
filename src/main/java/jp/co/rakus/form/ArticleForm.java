package jp.co.rakus.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 入力された名前と投稿を格納するフォーム.
 * 
 * @author wataru.saito
 *
 */
public class ArticleForm {

	/**
	 * 名前
	 */
	@NotBlank(message="名前は必須です。")
	@Size(max=50, message="名前は50字以内で入力してください。")
	private String name;
	/**
	 * 投稿記事
	 */
	@NotBlank(message="投稿は必須です。")
	@Size(max=50, message="50字以内で入力してください。")
	private String content;

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
