<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ensta.librarymanager.service.*" %>
<%@ page import="com.ensta.librarymanager.modele.*" %>


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
        <h1 class="page-announce-text valign">Emprunter un livre</h1>
      </div>
      <div class="row">
      <div class="container">
        <h5>Selectionnez le livre et le membre emprunteur</h5>
        <div class="row">
	      <form action="/TP3Ensta/emprunt_add" method="post" class="col s12">
	        <div class="row">
	          <div class="input-field col s6">
	            <select id="idLivre" name="idLivre" class="browser-default">
	              <option value="" disabled selected>-- Livres --</option>
	              <!-- TODO : parcourir la liste des livres disponibles et afficher autant d'options que n�cessaire, sur la base de l'exemple ci-dessous -->
                 <%! private List<Livre> livres = new ArrayList<Livre>();%>
                                		<% livres = (List)request.getAttribute("listeLivres");%>
                                		<%for(int i=0;i<livres.size();i++){ %>
                 		                  <option value="<%=livres.get(i).getId()%>"><%=livres.get(i).getTitre()%>, <%=livres.get(i).getAuteur()%></option>
                 		            <%} %>

	            </select>
	          </div>
	          <div class="input-field col s6">
	            <select id="idMembre" name="idMembre" class="browser-default">
	              <option value="" disabled selected>-- Membres --</option>
	              <!-- TODO : parcourir la liste des membres pouvant emprunter et afficher autant d'options que n�cessaire, sur la base de l'exemple ci-dessous -->
                  <%! private List<Membre> membres = new ArrayList<Membre>();%>
                                 		<% membres = (List)request.getAttribute("listeMembres");%>
                                 		<%for(int i=0;i<membres.size();i++){ %>
                  		                  <option value="<%=membres.get(i).getId()%>"><%=membres.get(i).getNom() + " " + membres.get(i).getPrenom()%></option>
                  		      		<%} %>

	            </select>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Enregistrer l'emprunt</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	    </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
