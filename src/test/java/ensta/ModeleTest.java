package ensta;
import com.ensta.librarymanager.modele.*;
import java.time.LocalDate;

public class ModeleTest {
    public static void main(String[] args) {

        Membre M = new Membre(5,"Mokni", "Amani", "17RJ", "amani@mokni.tn", "20453027", Abonnement.VIP);
        Livre L = new Livre(1,"livre1","woooo","ldfds");
        LocalDate d = LocalDate.now();
        Emprunt E = new Emprunt(7,M,L,d,d);
        System.out.println(E);
    }

}
