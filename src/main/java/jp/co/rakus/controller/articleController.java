package jp.co.rakus.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.domain.Article;
import jp.co.rakus.domain.Comment;
import jp.co.rakus.form.ArticleForm;
import jp.co.rakus.form.CommentForm;
import jp.co.rakus.repository.ArticleRepository;
import jp.co.rakus.repository.CommentRepository;

/**
 * 
 * 掲示板の入力と表示画面に飛ぶコントローラー.
 * 
 * @author wataru.saito
 *
 */
@Controller
@RequestMapping("/article")
public class articleController {

	@Autowired
	private ArticleRepository articleRepositry;
	@Autowired
	private CommentRepository commentRepository;

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	/**
	 * 
	 * 掲示板ページに飛ぶメソッド.
	 * 
	 * @param model
	 *            モデル
	 * @return 掲示板ページ
	 */
	@RequestMapping("/index")
	public String index(Model model) {

		List<Article> articleList = articleRepositry.findAll2();
		model.addAttribute("articleList", articleList);
		return "chat";
	}

	/**
	 * 
	 * 投稿内容をデータベースに送る.
	 * 
	 * @param form
	 *            入力された投稿内容
	 * @return indexメソッド
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(@Validated ArticleForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return index(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepositry.insert(article);

		return "redirect:/article/index";
	}

	/**
	 * コメント内容をデータベースに挿入する.
	 * 
	 * @param form
	 *            入力されたコメント
	 * @return indexメソッド
	 */
	@RequestMapping("/insertComment")
	public String insertComment(@Validated CommentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return index(model);
		}
		Comment comment = new Comment();
		int valueOfArticleId = Integer.parseInt(form.getArticleId());

		comment.setArticleId(valueOfArticleId);
		BeanUtils.copyProperties(form, comment);
		commentRepository.insert(comment);

		return "redirect:/article/index";
	}

	/**
	 * 
	 * 投稿を削除するメソッド.
	 * 
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/deleteById")
	public String deleteById(String articleId) {
		int intValueOfId = Integer.parseInt(articleId);
		articleRepositry.deleteById(intValueOfId);
		return "redirect:/article/index";
	}
}
