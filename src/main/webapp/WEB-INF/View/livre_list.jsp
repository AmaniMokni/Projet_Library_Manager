<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ensta.librarymanager.service.LivreServiceImpl" %>
<%@ page import="com.ensta.librarymanager.modele.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Liste des livres</h1>
      </div>
      <div class="row">
	        <div class="col s12">
	          <table class="striped no-padding">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Auteur</th>
                        <th>Code ISBN 13</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                

                    
                    <!-- TODO : parcourir la liste des livres et les afficher selon la structure d'exemple ci-dessus -->
                    <%! private List<Livre> livres = new ArrayList<Livre>();%>
                                   		<% livres = (List)request.getAttribute("listeLivres");%>
                                   		<%for(int i=0;i<livres.size();i++){ %>

                    	                    <tr>
                    	                        <td><%= livres.get(i).getTitre() %></td>
                    	                        <td><%= livres.get(i).getAuteur() %></td>
                    	                        <td><%= livres.get(i).getIsbn() %></td>
                    	                        <td class="center"><a href="livre_details?id=<%=livres.get(i).getId()%>"><ion-icon class="details" name="information-circle-outline"></ion-icon></a></td>
                    	                    </tr>
                           				<%}%>

                </tbody>
            </table>
          </div>
        </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
