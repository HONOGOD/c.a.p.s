<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<html>
<head>
    <title>Search Results</title>
</head>
<body>
    <h2>Search Results</h2>
    <ul>
        <% List<String> searchResults = (List<String>) session.getAttribute("searchResults");
        if (searchResults != null) {
            Iterator<String> iterator = searchResults.iterator();
            while (iterator.hasNext()) {
                String result = iterator.next();
                out.println("<li>" + result + "</li>");
            }
        } else {
            out.println("<li>No results found.</li>");
        } %>
    </ul>
</body>
</html>
