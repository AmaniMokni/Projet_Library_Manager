package ensta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.*;

public class ServiceTest {
    public static void main(String[] args) throws Exception {
        EmpruntServiceImpl E = EmpruntServiceImpl.getInstance();
        MembreServiceImpl M = MembreServiceImpl.getInstance();
        LivreServiceImpl L = LivreServiceImpl.getInstance();
        System.out.println(L.getList().size());
        System.out.println(L.getListDispo().size());
        int id = M.create("mokni", "amani", "Tunis", "amani@gmail.com", "1111111144");
        E.create(id,L.getListDispo().get(0).getId(), LocalDate.now());
        System.out.println(L.getListDispo().size());
        Livre l = L.getList().get(0);
        System.out.println("le nom du livre avant changement est : "+l.getTitre());
        int idLivre=l.getId();
        l.setTitre("Viking");
        L.update(l);
        Livre l1=L.getById(idLivre);
        System.out.println("Le nom du livre apres changement est : "+l1.getTitre());



    }
    }
