package board.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import board.recipe.dto.RecipeDTO;
import common.DBConnPool;

public class RecipeDAO extends DBConnPool {
	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;

	// 레시피 순서 조회
	public List<RecipeDTO> selectRecipeSteps(String recipeId) {
		List<RecipeDTO> list = new ArrayList<>();
		String query = "SELECT * FROM recipe WHERE recipeId = ? ORDER BY recipeStep";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);

			try (ResultSet rs = psmt.executeQuery()) {
				while (rs.next()) {
					RecipeDTO dto = new RecipeDTO();
					dto.setRecipeStep(rs.getInt("recipeStep"));
					dto.setRecipeId(rs.getInt("recipeId"));
					dto.setStepContent(rs.getString("stepContent"));
					dto.setImgOName(rs.getString("imgOName"));
					dto.setImgSName(rs.getString("imgSName"));
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
			DBConnPool.close(rs);
		}

		return list;
	}

	// 레시피 순서 등록
	public void insertRecipeStep(RecipeDTO dto) {
		String query = "INSERT INTO recipe (recipeStep, recipeId, stepContent, imgOName, imgSName) "
				+ "VALUES (?, ?, ?, ?, ?)";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getRecipeStep());
			psmt.setInt(2, dto.getRecipeId());
			psmt.setString(3, dto.getStepContent());
			psmt.setString(4, dto.getImgOName());
			psmt.setString(5, dto.getImgSName());
			int result = psmt.executeUpdate();
			System.out.println("Inserted rows: " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}

	// 레시피 순서 삭제
	public void deleteRecipeSteps(String recipeId) {
		String query = "DELETE FROM recipe WHERE recipeId = ?";
		con = DBConnPool.getConnection();
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, recipeId);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnPool.close(con);
			DBConnPool.close(psmt);
		}
	}
}