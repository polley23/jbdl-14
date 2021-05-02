package com.gfg.jbdl14restfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Movie movie) {
        Movie movie1 = findByName(movie.getName());
        if(movie1 == null){
            String query = "INSERT INTO movie\n" +
                    "(`name`,`genre`,`language`)\n"+
                    "VALUES\n" +
                    "(?,?,?)";
            jdbcTemplate.update(query, movie.getName(),
                    movie.getGenre(),movie.getLanguage());
            return;
        }
        String sql1 = "Update movie\n" +
                "set `language` = ? ,\n" +
                "`genre` = ? \n" +
                "WHERE `name` = ?";
        jdbcTemplate.update(sql1, movie.getLanguage(),movie.getGenre(), movie.getName());

//        String sql2 = "Update movie\n" +
//                "set `genre` = ? \n" +
//                "WHERE `name` = ?";
//        jdbcTemplate.update(sql2, movie.getGenre(),movie.getName());


    }

    @Override
    public Movie findByName(String name) {
        String sql  = "SELECT * FROM movie\n" +
                "WHERE `name` = ?";
        List<Movie> movies = jdbcTemplate
                .query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, name);
                    }
                }, new RowMapper<Movie>() {
                    @Override
                    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Movie
                                .builder()
                                .name(resultSet.getString(2))
                                .genre(resultSet.getString(3))
                                .language(resultSet.getString(4))
                                .build();
                    }
                });
        if(movies == null || movies.size() == 0){
            return null;
        }

        return movies.get(0);
    }

    @Override
    public List<Movie> findByLanguage(String language) {
        String sql  = "SELECT * FROM movie\n" +
                "WHERE `language` = ?";
        List<Movie> movies = jdbcTemplate
                .query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, language);
                    }
                }, new RowMapper<Movie>() {
                    @Override
                    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Movie
                                .builder()
                                .name(resultSet.getString(2))
                                .genre(resultSet.getString(3))
                                .language(resultSet.getString(4))
                                .build();
                    }
                });
        if(movies == null || movies.size() == 0){
            return null;
        }
        return movies;

    }

    @Override
    public List<Movie> findByGenre(String genre) {
        String sql  = "SELECT * FROM movie\n" +
                "WHERE `genre` = ?";
        List<Movie> movies = jdbcTemplate
                .query(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, genre);
                    }
                }, new RowMapper<Movie>() {
                    @Override
                    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Movie
                                .builder()
                                .name(resultSet.getString(2))
                                .genre(resultSet.getString(3))
                                .language(resultSet.getString(4))
                                .build();
                    }
                });
        if(movies == null || movies.size() == 0){
            return null;
        }
        return movies;
    }

    @Override
    public void delete(String name) {
        String sql  = "DELETE FROM movie\n" +
                "WHERE `name` = ?";
        jdbcTemplate.update(sql,name);


    }
}
