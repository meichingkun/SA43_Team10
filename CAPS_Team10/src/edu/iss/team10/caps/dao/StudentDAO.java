package edu.iss.team10.caps.dao;

import java.util.ArrayList;

import edu.iss.team10.caps.exception.DAOException;
import edu.iss.team10.caps.exception.MyDataException;
import edu.iss.team10.caps.model.StudentDTO;
import edu.iss.team10.caps.model.StudentSearchDTO;

public interface StudentDAO {

	public StudentDTO findStudent(String studentId) throws DAOException, MyDataException;

	public ArrayList<StudentDTO> findAllStudent() throws DAOException, MyDataException;

	public ArrayList<StudentDTO> findStudentByCriteria(StudentSearchDTO studentSearchDTO) throws DAOException, MyDataException;
	
	public int insertStudent(StudentDTO student) throws DAOException, MyDataException;

	public int updateStudent(StudentDTO student) throws DAOException, MyDataException;

	public int deleteStudent(StudentDTO student) throws DAOException, MyDataException;
	
}
