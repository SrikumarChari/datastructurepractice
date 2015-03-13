/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author schari
 */
public class placeholder {
    /*
    		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		// Query q = em.createQuery("SELECT s FROM StudentEntity s");
		// List<StudentEntity> studentList = q.getResultList();
		// for (StudentEntity stud : studentList)
		// System.out.println(stud);
		// System.out.println("Student Size: " + studentList.size());
		// System.out.println();

		// q = em.createQuery("SELECT a FROM ApplyEntity a");
		// List<ApplyEntity> applyList = q.getResultList();
		// for (ApplyEntity app : applyList) {
		// System.out.println(app);
		// }
		// System.out.println("Apply Database Size: " + applyList.size());
		// System.out.println();
		//
		// q = em.createQuery("SELECT c FROM CollegeEntity c");
		// List<CollegeEntity> collegeList = q.getResultList();
		// for (CollegeEntity col : collegeList) {
		// System.out.println(col);
		// }
		// System.out.println("College Size: " + collegeList.size());

		// Query database to find names of all students and the major(s) they
		// applied to. The keyword DISTINCT ensures that the name is not
		// repeated if the student applied to the same major in different colleges
		System.out.println("Simple Join");
		Query q = em.createQuery("SELECT DISTINCT s.sName, a.major FROM StudentEntity s, ApplyEntity a WHERE s.sID = a.sID");
		@SuppressWarnings("unchecked")
		List<Object> results = q.getResultList();
		for (Object result : results) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		//find student name, GPA and decision of student who graduated from a school with less than 1000, applied for CS at Stanford
		System.out.println("Complicated join");
		q = em.createQuery("SELECT DISTINCT s.sName, s.GPA, a.decision FROM StudentEntity s, ApplyEntity a WHERE s.sID = a.sID AND s.sizeHS < 1000 AND a.major = 'CS' AND a.cName = 'Stanford'");
		@SuppressWarnings("unchecked")
		List<Object> results1 = q.getResultList();
		for (Object result : results1) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		q = em.createQuery("SELECT s.sID, s.sName, s.GPA, a.cName, c.enrollment FROM StudentEntity s, ApplyEntity a, CollegeEntity c"
				+ " WHERE s.sID = a.sID AND a.cName = c.cName ORDER BY s.GPA DESC, c.enrollment");
		@SuppressWarnings("unchecked")
		List<Object> results11 = q.getResultList();
		for (Object result : results11) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("LIKE example");
		q = em.createQuery("SELECT DISTINCT s.sID, a.major FROM StudentEntity s, ApplyEntity a WHERE a.major LIKE '%bio%'");
		@SuppressWarnings("unchecked")
		List<Object> results12 = q.getResultList();
		for (Object result : results12) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("Two variables in a query");
		//this is also used to work around the lack of UNION and INTERSECT operators
		q = em.createQuery("SELECT s1.sID, s1.sName, s1.GPA, s2.sID, s2.sName, s2.GPA FROM StudentEntity s1, StudentEntity "
				+ "s2 WHERE s1.GPA = s2.GPA AND s1.sID <> s2.sID");
		@SuppressWarnings("unchecked")
		List<Object> results2 = q.getResultList();
		for (Object result : results2) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("Subquery example\n\nFind all GPA's of students who applied to CS");		
		q = em.createQuery("SELECT DISTINCT s.GPA FROM StudentEntity s, ApplyEntity a WHERE s.sID = a.sID AND a.major = 'CS'");
		//NOTE: this query is wrong ... I wrote it to demonstrate that in certain sub-queries have to be used.
		//		In this case if a student applied for CS to 'n' colleges they will be counted 'n' times which is wrong
		@SuppressWarnings("unchecked")
		List<Float> results3 = q.getResultList();
		for (Float result : results3)
			System.out.println(result);
		System.out.println();

		System.out.println("The correct list with sub-queries");		
		q = em.createQuery("SELECT s.GPA FROM StudentEntity s WHERE s.sID IN (SELECT DISTINCT a.sID "
				+ "FROM ApplyEntity a WHERE a.major = 'CS')");
		//The sub-query first gets a distinct list of student ID's of students who applied for CS, 
		//the master query then "joins" this list with the list of student ID's and then selects the GPA
		List<Float> results4 = q.getResultList();
		for (Float result : results4)
			System.out.println(result);
		System.out.println();
		
		System.out.println("\"WHERE\" Example: Name of a college if there at least one other in the same state");		
		q = em.createQuery("SELECT c1.cName, c1.state FROM CollegeEntity c1 WHERE EXISTS (SELECT c2 FROM CollegeEntity c2 WHERE c1.state = c2.state"
				+ " AND c1.cName <> c2.cName)");
		List<Object> results5 = q.getResultList();
		for (Object result : results5) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("\"WHERE\" and \"ALL\" Example: College with the highest enrollment");		
		q = em.createQuery("SELECT c1.cName FROM CollegeEntity c1 WHERE c1.enrollment > ALL (SELECT c2.enrollment FROM CollegeEntity c2 "
				+ "WHERE c1.cName <> c2.cName)");
		List<String> results6 = q.getResultList();
		for (String result : results6)
			System.out.println(result);
		System.out.println();

		System.out.println("\"ANY\" Example: Students who are NOT from the smallest high school");		
		q = em.createQuery("SELECT s.sID, s.sName, s.sizeHS FROM StudentEntity s WHERE s.sizeHS > ANY (SELECT s2.sizeHS FROM StudentEntity s2) ORDER BY s.sizeHS DESC");
		List<Object> results7 = q.getResultList();
		for (Object result : results7) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("\" INNER JOIN\" Example: List of Students and the majors they applied to");		
		q = em.createQuery("SELECT DISTINCT s.sName, s.sID, a.major FROM StudentEntity s INNER JOIN ApplyEntity a WHERE s.sID = a.sID ORDER BY s.sName");
		List<Object> results8 = q.getResultList();
		for (Object result : results8) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("\"INNER JOIN\" Example: List of Students and the majors they applied to");		
		q = em.createQuery("SELECT DISTINCT a.sID, s.sName, s.GPA, a.cName, c.enrollment FROM StudentEntity s INNER JOIN ApplyEntity a, CollegeEntity c WHERE a.sID = s.sID AND a.cName = c.cName");
		List<Object> results9 = q.getResultList();
		for (Object result : results9) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++)
				System.out.print(subResultList[i].toString() + "\t");
			System.out.println();
		}
		System.out.println();

		System.out.println("\"OUTER JOIN\" Example: List of Students and the majors they applied to");		
		q = em.createQuery("SELECT DISTINCT s.sName, s.sID, a.cName, a.major FROM ApplyEntity a LEFT JOIN StudentEntity s ON s.sID = a.sID "
				+ "UNION "
				+ "SELECT DISTINCT s.sName, s.sID, a.cName, a.major FROM StudentEntity s LEFT JOIN ApplyEntity a ON s.sID = a.sID");
		List<Object> results10 = q.getResultList();
		for (Object result : results10) {
			// assumes each object is an object of objects based on the query
			// request which is to get student name and major they applied to
			Object[] subResultList = (Object[]) result;
			for (int i = 0; i < subResultList.length; i++) {
				//the left join may have created null elements
				if (subResultList[i] == null)
					System.out.print("*NULL*\t");
				else
					System.out.print(subResultList[i].toString() + "\t");
			}
			System.out.println();
		}
		System.out.println();

		em.close();

    */
}
