<%@page import="view.AdminViewManager, controller.Event.*"%>
<%@ page contentType="application/json" %>
<%= getEvents(request)%>
<%@ page import="com.dhtmlx.planner.*,view.AdminViewManager.*, controller.Event.*" %>

<%!
    String getEvents(HttpServletRequest request) throws Exception {
        
    AdminViewManager evs = new AdminViewManager(request);
    
     return      evs.run();   
  }
%>
