package ensta;

import java.time.LocalDate;
import java.util.ArrayList;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.modele.*;

public class DaoTest {
    public static void main(String[] args) throws Exception {
       /*test de la classes LivreDaoImpl */
        /*LivreDaoImpl dao = LivreDaoImpl.getInstance();
        ArrayList<Livre> livres = (ArrayList<Livre>) dao.getList() ;
		System.out.println(livres);
		Livre livre = dao.getById(10);
		System.out.println(livre);
		int nb = dao.count();
		System.out.println(nb);*/

        /* test de la classe MembreDaoImpl */
       /* MembreDaoImpl daoM = MembreDaoImpl.getInstance();
        System.out.println(daoM.getList());
        System.out.println(daoM.getById(12));
        daoM.create( "mokni",  "amani", "palaiseau", "amani.mokni@gmail.com", "20453027");
        daoM.update(new Membre(15,"mokni","amani","palaiseau","adresse mail","1444456",Abonnement.VIP));
        //daoM.delete(13);
        System.out.println(daoM.getList());
        System.out.println(daoM.count());
        //daoM.delete(12);*/

        /* test de la classe EmpruntDaoImpl */
        EmpruntDaoImpl daoE = EmpruntDaoImpl.getInstance();
        System.out.println(daoE.getList());
        System.out.println(daoE.getListCurrent());
        System.out.println(daoE.getListCurrentByLivre(8));
        System.out.println(daoE.getById(5));
        daoE.create(4, 5, LocalDate.now());
        System.out.println(daoE.count());


    }

}



