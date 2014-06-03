

    <%@page import="view.StudentViewManager"%>
    <%@ page contentType="application/json" %>
    <%= getEvents(request)%>
    <%@ page import="com.dhtmlx.planner.*,view.StudentViewManager.*" %>


    <%!
        String getEvents(HttpServletRequest request) throws Exception {
            StudentViewManager evs = new StudentViewManager(request);
            return evs.run();
        }
    %>
