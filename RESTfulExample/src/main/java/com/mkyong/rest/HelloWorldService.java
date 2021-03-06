package com.mkyong.rest;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Path("/hello")
public class HelloWorldService {

    /*******************************************************
      Common Connector snippet
     ******************************************************/

    public Connection getConnected(){
        Connection conn = null;
        try{
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            conn = ds.getConnection();
        } catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    /*******************************************************
      To GET the details of a particular Employee
     *******************************************************/

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/details")
        public Employee empDetails(@QueryParam("id") int id) {
            Connection conn = null;
            String output = "";
            PreparedStatement pstmt = null;
            Employee emp = null;
            try {
                conn = getConnected();
                String sql = "Select * from employee where id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,id);
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setAddress(rs.getString("address"));
                emp.setContact(rs.getLong("contact"));
                emp.setSalary(rs.getFloat("salary"));
            } catch(Exception e) {
                output = e.toString();
            }
            return emp;
        }

    /**************************************************************
      To list the employee table
      **************************************************************/

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/list")
        public List<Employee> listTable() {
            Connection conn = null;
            List<Employee> empList = new ArrayList<Employee>();
            //Employee emp = null;
            Statement stmt = null;
            try {
                conn = getConnected();
                stmt = conn.createStatement();
                String sql = "select * from employee";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    Employee emp = new Employee();
                    emp.setName(rs.getString("name"));
                    emp.setId(rs.getInt("id"));
                    emp.setEmail(rs.getString("email"));
                    emp.setAddress(rs.getString("address"));
                    emp.setContact(rs.getLong("contact"));
                    emp.setSalary(rs.getFloat("salary"));
                    //add this emp object to the empList object
                    empList.add(emp);
                }
                rs.close();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{if(conn!=null)
                    conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }

            //return Response.status(200).entity(output).build();
            return empList;
        }

        /****************************************************************
          To update an existing employee
          ***************************************************************/

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/update")
        public String updateEmployee(@QueryParam("id") int id) {

            Connection conn = null;
            Statement stmt = null;
            Employee emp = null;
            String output = "Updated ";

            try{
                conn = getConnected();
                stmt = conn.createStatement();
                String sql = "update employee set address = 'address' where id = " + id;
                int updated = stmt.executeUpdate(sql);
                output = output + updated + "records successfully";
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if(stmt != null)
                        stmt.close();
                } catch(SQLException se2) {
                }// nothing we can do
                try {
                    if(conn != null)
                        conn.close();
                } catch(SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
            return output;
        }

        /****************************************************************
          To Delete an existing employee
          ***************************************************************/

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/delete")
        public String deleteEmployee(@QueryParam("id") int id) {

            Connection conn = null;
            PreparedStatement stmt = null;
            Employee emp = null;
            String output = "Deleted ";

            try{
                conn = getConnected();
                String sql = "delete from employee where id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                output = output + " record with id:" + id + " successfully";
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if(stmt != null)
                        stmt.close();
                } catch(SQLException se2) {
                }// nothing we can do
                try {
                    if(conn != null)
                        conn.close();
                } catch(SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
            return output;
        }


        /****************************************************************
          To Insert a new employee record
          ***************************************************************/

        @POST
        @Produces(MediaType.TEXT_PLAIN)
        @Consumes("application/json")
        @Path("/insert")
        public String insertEmployee(Employee emp) {

            Connection conn = null;
            PreparedStatement pstmt = null;
            //Employee emp = null;
            String output = "Updated ";

            try{
                conn = getConnected();
                //stmt = conn.createStatement();
                String sql = "insert into employee(id, name, email, address, contact, salary) values(?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, emp.getId());
                pstmt.setString(2, emp.getName());
                pstmt.setString(3, emp.getEmail());
                pstmt.setString(4, emp.getAddress());
                pstmt.setLong(5, emp.getContact());
                pstmt.setFloat(6, emp.getSalary());
                int updated = pstmt.executeUpdate();
                output = output + updated + "records successfully";
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if(pstmt != null)
                        pstmt.close();
                } catch(SQLException se2) {
                }// nothing we can do
                try {
                    if(conn != null)
                        conn.close();
                } catch(SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
            return output;
        }
}
