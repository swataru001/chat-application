package jp.co.rakus.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Article;
import jp.co.rakus.domain.Comment;

/**
 * 
 * articlesテーブルのレポジトリ.
 * @author wataru.saito
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Article> ARTICLE_ROWMAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	private final static ResultSetExtractor<List<Article>> ARTICLE_RESULTSETEXTRACTOR = (rs) ->{
		
		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = null;
		int previousId = 0;
		while (rs.next()) {
			int articleId = rs.getInt("article_id");
			if (previousId != articleId) {
				Article article = new Article();
				article.setId(articleId);
				article.setName(rs.getString("article_name"));
				article.setContent(rs.getString("article_content"));
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				articleList.add(article);
			}

			if(rs.getString("comment_name")!=null) {
			Comment comment = new Comment();
			comment.setName(rs.getString("comment_name"));
			comment.setContent(rs.getString("comment_content"));
			commentList.add(comment);
			previousId = articleId;
			}
		}
		return articleList;
	};
	/**
	 * 記事の全件検索.
	 * 
	 * @return　記事全件
	 */
	public List<Article> findAll() {
		String sql = "SELECT id,name,content From articles order by id desc;";


		List<Article> articleList = template.query(sql, ARTICLE_ROWMAPPER);
		return articleList;
	}

	/**
	 * 投稿の全件検索
	 * @return　投稿全件
	 */
	public List<Article> findAll2() {
		String sql = "SELECT a.id AS article_id , a.name AS article_name , a.content AS article_content , c.name AS comment_name , c.content AS comment_content FROM articles AS a left outer join comments AS c on a.id = c.article_id order by article_id desc;";

		
		List<Article> articleList = template.query(sql, ARTICLE_RESULTSETEXTRACTOR);
		return articleList;
	}

	/**
	 * 投稿挿入のためのメソッド.
	 * @param article　挿入する投稿のデータを持つarticleオブジェクト.
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String insertSql = "INSERT INTO articles(name,content)values(:name,:content);";
		template.update(insertSql, param);
	}

	/**
	 * 
	 * 記事とコメント削除のためのメソッド.
	 * @param id 削除したい投稿の投稿ID
	 */
	public void deleteById(int id) {
		System.out.println(id);
		//投稿とコメントを一括で削除する
		String deleteSql = "WITH deleted AS (DELETE FROM articles WHERE id = :id RETURNING id)\r\n" + 
				"DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		template.update(deleteSql, param);
	}
}
