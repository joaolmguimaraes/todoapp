/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author jlmgu
 */
public class TaskController {
    
        public void save(Task task) {
            String sql = "INSERT INTO tasks (idProject, "
                    + "name, "
                    + "description, "
                    + "deadline, "
                    + "createdAt, "
                    + "updatedAt) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            Connection connection = null;
            PreparedStatement statement = null;
            
            try {
                
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, task.getIdProject());
                statement.setString(2, task.getName());
                statement.setString(3, task.getDescription());
                statement.setBoolean(4, task.isIsCompleted());
                statement.setString(5, task.getNotes());
                statement.setDate(6, new Date(task.getDeadline().getTime()));
                statement.setDate(7, new Date(task.getCreatedAt().getTime()));
                statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
                statement.execute();
                
            } catch (Exception e) {
                throw new RuntimeException("Erro ao salvar tarefa " 
                        + e.getMessage(), e);
            } finally {
                ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) connection);
              
                }
            }
        
        public void update(Task task) {
            String sql = "UPDATE tasks SET "
            + "idproject = ?, "
            + "name = ?, "
            + "description = ?, "
            + "notes = ?, "
            + "completed = ?, "
            + "deadline = ?, "
            + "createdAt = ?, "
            + "updatedAt = ?, "
            + "WHERE id = ?";
            
            Connection connection = null;
            PreparedStatement statement = null;
            
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, task.getIdProject());
                statement.setString(2, task.getName());
                statement.setString(3, task.getDescription());
                statement.setString(4, task.getNotes());
                statement.setBoolean(5, task.isIsCompleted());
                statement.setDate(6, new Date(task.getDeadline().getTime()));
                statement.setDate(7, new Date(task.getCreatedAt().getTime()));
                statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
                statement.setInt(9, task.getId());
                statement.execute();                
                
            } catch (Exception e) {
                throw new RuntimeException("Erro ao atualizar a tarefa" +
                        e.getMessage() + e);
            }
        }
        
        public void removeById(int taskId) throws SQLException {
            
            String sql = "DELETE FROM tasks WHERE id = ?";
            
            Connection connection = null;
            PreparedStatement statement = null;
            
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, taskId);
                statement.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro ao excluir tarefa.");
            } finally {
                ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) connection, statement);
            }
        }
        
        public List<Task> getAll(int idProject) {
            
            String sql = "SELECT * FROM tasks WHERE idProject = ?";
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            
            List<Task> tasks = new ArrayList<>();
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, idProject);
                resultSet = statement.executeQuery();
                
                while(resultSet.next()) {
                    
                    Task task = new Task();
                    task.setId(resultSet.getInt("id"));
                    task.setIdProject(resultSet.getInt("idProject"));
                    task.setName(resultSet.getString("name"));
                    task.setDescription(resultSet.getString("description"));
                    task.setNotes(resultSet.getString("notes"));
                    task.setIsCompleted(resultSet.getBoolean("completed"));
                    task.setDeadline(resultSet.getDate("deadline"));
                    task.setCreatedAt(resultSet.getDate("createdAt"));
                    task.setUpdatedAt(resultSet.getDate("updatedAt"));
                    
                    tasks.add(task);
                    
                }
                
            } catch (Exception e) {
                throw new RuntimeException("erro" + e.getMessage(), e);
              
            } finally {
                ConnectionFactory.closeConnection((com.mysql.jdbc.Connection) connection, statement, resultSet);
            }
            
            return tasks;
        }
}
