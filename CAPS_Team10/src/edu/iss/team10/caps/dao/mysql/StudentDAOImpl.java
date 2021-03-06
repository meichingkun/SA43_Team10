package edu.iss.team10.caps.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.iss.team10.caps.dao.ConnectionHandler;
import edu.iss.team10.caps.dao.StudentDAO;
import edu.iss.team10.caps.exception.DAOException;
import edu.iss.team10.caps.exception.MyDataException;
import edu.iss.team10.caps.model.StudentDTO;
import edu.iss.team10.caps.model.StudentSearchDTO;

public class StudentDAOImpl implements StudentDAO {

	private ResultSet rs;

	@Override
	public StudentDTO findStudent(String studentId) throws DAOException, MyDataException {
		StudentDTO studentDTO = null;
		Connection connection = ConnectionHandler.openConnection();
		PreparedStatement pstatement = null;

		String select = "SELECT * FROM caps.student WHERE studentId=?";

		try {
			pstatement = connection.prepareStatement(select);
			pstatement.setString(1, studentId);
			rs = pstatement.executeQuery();
			while (rs.next()) {
				studentDTO = new StudentDTO(rs.getString("studentId"), rs.getString("studentName"),
						rs.getString("studentEmail"), rs.getString("studentPhoneNumber"),
						rs.getString("studentAddress"), rs.getDate("enrolmentDate"));
			}
			if (studentDTO == null) {
				throw new MyDataException("There is no Student Info!");
			}
		} catch (SQLException e) {
			System.err.println("Error: Unable to Select Student info from database.\n" + e.getMessage());
		} finally {
			ConnectionHandler.closeConnection(connection, pstatement);
		}
		return null;
	}

	@Override
	public ArrayList<StudentDTO> findAllStudent() throws DAOException, MyDataException {
		ArrayList<StudentDTO> result = new ArrayList<StudentDTO>();
		Connection connection = ConnectionHandler.openConnection();
		PreparedStatement pstatement = null;

		String select = "SELECT * FROM caps.student;";
		try {
			pstatement = connection.prepareStatement(select);
			rs = pstatement.executeQuery();
			while (rs.next()) {
				StudentDTO studentDTO = new StudentDTO(rs.getString("studentId"), rs.getString("studentName"),
						rs.getString("studentEmail"), rs.getString("studentPhoneNumber"),
						rs.getString("studentAddress"), rs.getDate("enrolmentDate"));
				result.add(studentDTO);
			}
			if (result.size() == 0) {
				throw new MyDataException("There is no Student Info!");
			}
		} catch (SQLException e) {
			System.err.println("Error: Unable to retrieve all stuedent info from database.\n" + e.getMessage());
		} finally {
			ConnectionHandler.closeConnection(connection, pstatement);
		}
		return result;
	}

	@Override
	public ArrayList<StudentDTO> findStudentByCriteria(StudentSearchDTO studentSearchDTO)
			throws DAOException, MyDataException {
		ArrayList<StudentDTO> result = new ArrayList<StudentDTO>();
		Connection connection = ConnectionHandler.openConnection();
		PreparedStatement pstatement = null;

		String select = null;
		if (studentSearchDTO.getStudentName().trim().equalsIgnoreCase("")) {

			select = "SELECT * FROM caps.student WHERE studentName LIKE '" + studentSearchDTO.getStudentName().trim()
					+ "%';";
		} else {
			if (studentSearchDTO.getStudentName().trim().equalsIgnoreCase("")) {
				select = "SELECT * FROM caps.student WHERE studentName LIKE '"
						+ studentSearchDTO.getStudentName().trim() + "%';";
			} else {
				select = "SELECT * FROM caps.student WHERE studentId LIKE '" + studentSearchDTO.getStudentId().trim()
						+ "%' AND studentName LIKE '" + studentSearchDTO.getStudentName() + "%';";
			}
		}
		try {
			pstatement = connection.prepareStatement(select);
			rs = pstatement.executeQuery();
			while (rs.next()) {
				StudentDTO studentDTO = new StudentDTO(rs.getString("studentId"), rs.getString("studentName"),
						rs.getString("studentEmail"), rs.getString("studentPhoneNumber"),
						rs.getString("studentAddress"), rs.getDate("enrolmentDate"));
				result.add(studentDTO);
			}
			if (result.size() == 0) {
				throw new MyDataException("There is no Student Info!");
			}
		} catch (SQLException e) {
			System.err.println("Error: Unable to retrieve all stuedent info from database.\n" + e.getMessage());
		} finally {
			ConnectionHandler.closeConnection(connection, pstatement);
		}
		return result;
	}

	@Override
	public int insertStudent(StudentDTO student) throws DAOException, MyDataException {
		int result = 0;
		Connection connection = ConnectionHandler.openConnection();
		PreparedStatement pstatement = null;
		java.sql.Date enrolmentDate = new java.sql.Date(student.getEnrolmentDate().getTime());

		String ins = "INSERT INTO caps.student(studentId, studentName, studentEmail, studentPhoneNumber, studentAddress, enrolmentDate) "
				+ "VALUES (?,?,?,?,?,?)";
		try {
			pstatement = connection.prepareStatement(ins);
			pstatement.setString(1, student.getStudentId());
			pstatement.setString(2, student.getStudentName());
			pstatement.setString(3, student.getStudentEmail());
			pstatement.setString(4, student.getStudentPhoneNumber());
			pstatement.setString(5, student.getStudentAddress());
			pstatement.setDate(6, enrolmentDate);

			result = pstatement.executeUpdate();
			if (result <= 0) {
				throw new MyDataException("FAIL! Insert Specific Student!");
			} else {
				System.out.println("Success ! Insert Student!");
			}
		} catch (SQLException e) {
			System.err.println("Error: Unable to insert Student info to database.\n" + e.getMessage());
		} finally {
			ConnectionHandler.closeConnection(connection, pstatement);
		}
		return result;
	}

	@Override
	public int updateStudent(StudentDTO student) throws DAOException, MyDataException {
		int result = 0;
		Connection connection = ConnectionHandler.openConnection();
		PreparedStatement pstatement = null;
		java.sql.Date enrolmentDate = new java.sql.Date(student.getEnrolmentDate().getTime());

		String ins = "UPDATE caps.student SET studentName=?, studentEmail=?, studentPhoneNumber=?, studentAddress=?, enrolmentDate=? WHERE studentId = ?; ";

		try {
			pstatement = connection.prepareStatement(ins);
			pstatement.setString(1, student.getStudentName());
			pstatement.setString(2, student.getStudentEmail());
			pstatement.setString(3, student.getStudentPhoneNumber());
			pstatement.setString(4, student.getStudentAddress());
			pstatement.setDate(5, enrolmentDate);
			pstatement.setString(6, student.getStudentId());

			result = pstatement.executeUpdate();
			if (result <= 0) {
				throw new MyDataException("FAIL! Update Specific Student!");
			} else {
				System.out.println("Success ! Update Student!");
			}
		} catch (SQLException e) {
			System.err.println("Error: Unable to Update Student info to database.\n" + e.getMessage());
		} finally {
			ConnectionHandler.closeConnection(connection, pstatement);
		}
		return result;
	}

	@Override
	public int deleteStudent(StudentDTO student) throws DAOException, MyDataException {
		int result = 0;
		Connection connection = ConnectionHandler.openConnection();
		PreparedStatement pstatement = null;

		String ins = "DELETE FROM caps.student WHERE studentId = ?; ";

		try {
			pstatement = connection.prepareStatement(ins);
			pstatement.setString(1, student.getStudentId());

			result = pstatement.executeUpdate();
			if (result <= 0) {
				throw new MyDataException("FAIL! Delete Specific Student!");
			} else {
				System.out.println("Success ! Delete Student!");
			}
		} catch (SQLException e) {
			System.err.println("Error: Unable to Delete Student info from database.\n" + e.getMessage());
		} finally {
			ConnectionHandler.closeConnection(connection, pstatement);
		}
		return result;
	}

}
