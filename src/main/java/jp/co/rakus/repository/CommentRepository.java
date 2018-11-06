package jp.co.rakus.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Comment;

/**
 * commentsテーブルのレポジトリ.
 * @author wataru.saito
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static RowMapper<Comment> COMMENT_ROWMAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		return comment;
	};

	/**
	 * 
	 * コメントを投稿Idをもとに検索するメソッド.
	 * @param id 投稿Id
	 * @return　検索したコメントリスト
	 */
	public List<Comment> findById(Integer id) {
		String sql = "SELECT id,name,content from comments where article_id = :id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROWMAPPER);
		return commentList;
	}

	/**
	 * 
	 * コメントを挿入するメソッド.
	 * @param comment 
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String insertSql = "INSERT INTO comments (name,content,article_id)values(:name,:content,:articleId);";
		template.update(insertSql, param);
	}

}
