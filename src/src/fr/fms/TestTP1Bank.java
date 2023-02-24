package src.fr.fms;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import src.fr.fms.business.IBankImpl;
import src.fr.fms.entities.Current;
import src.fr.fms.entities.Customer;
import src.fr.fms.entities.Saving;

/**
 * Dans cette version, nous souhaitons dorénavant établir une interaction avec l’utilisateur de sorte
 *qu’il pourra saisir un numéro de compte bancaire, s’il existe, il aura accès à la liste des opérations
 *sur ce compte, sinon un message remonté via le mécanisme des exceptions indiquera que le compte
 *n’existe pas. De même, la gestion des cas particuliers se fera dorénavant via les exceptions
 *capturées et gérées permettant donc la continuité de l’appli tout en informant l utilisateur sur les
 *problèmes rencontrés : « retrait impossible, solde insuffisant ».
 *NB
 *: Vous pouvez, pour des raisons pédagogiques, choisir de garder la version actuelle de l’appli en
 *l’état, dans ce cas, il faut réaliser un nouveau projet [AdvTP1Bank1.1] par exemple, y ajouter les
 *packages de l’actuel p rojet, bien tester puis effectuer les modifications.
 *Vous pouvez dorénavant utiliser tous les concepts abordés tels que
 *: exceptions, expressions
 *régulières , lambda, stream
 * @author ArenasA
 *
 */
public class TestTP1Bank {
	public static void main(String[] args) {
		IBankImpl bankJob = new IBankImpl();

		//création de quelques clients
		Customer robert = new Customer(1, "dupont", "robert", "robert.dupont@amail.com");
		Customer julie = new Customer(2, "jolie", "julie", "julie.jolie@zmail.com");
		Customer didier = new Customer(1, "super", "didier", "didier.super@gmail.com");
		Customer roger = new Customer(1, "durandil", "roger", "durandil.roger@gmail.com");
		Customer perlouze = new Customer(1, "jewel", "ruby", "jewel.ruby@hotmail.fr");
		//création de quelques comptes courant
		Current firstCurrentAccount = new Current(1002003001, new Date(), 1500, 200 , robert);
		Current secondCurrentAccount = new Current(1002003002, new Date(), 10500, 500 , didier);
		Current thirdCurrentAccount = new Current(1002003003, new Date(), 19500, 2100 , roger);
		Current fourthCurrentAccount = new Current(1002003004, new Date(), 91500, 8200 , perlouze);

		//création de quelsque comptes épargne
		Saving firstSavingAccount = new Saving(2003004001, new Date(), 2000, 5.5, julie);
		Saving secondSavingAccount = new Saving(2003004002, new Date(), 2000, 5.5, didier);
		Saving thirdSavingAccount = new Saving(2003004003, new Date(), 2000, 5.5, perlouze);

		bankJob.addAccount(firstCurrentAccount);
		bankJob.addAccount(secondCurrentAccount);
		bankJob.addAccount(thirdCurrentAccount);
		bankJob.addAccount(fourthCurrentAccount);
		bankJob.addAccount(firstSavingAccount);
		bankJob.addAccount(secondSavingAccount);
		bankJob.addAccount(thirdSavingAccount);

		int userChoice = 0;
		Scanner scan = new Scanner(System.in);
		long accountTyped = (long) 0;


		while(!bankJob.isAccountExists(accountTyped)) {
			try {
				System.out.println("Saisissez un numéro de compte valide.");
				accountTyped = scan.nextLong();
				System.out.println("Bienvenue " 
						+ bankJob.consultAccount(accountTyped).getCustomer().getFirstName()  + " "
						+ bankJob.consultAccount(accountTyped).getCustomer().getName()
						+", que souhaitez-vous faire ? tapez le numéro correspondant");

				while(userChoice != 6) {
					displayMenu();
					userChoice = scan.nextInt();

					try {
						switch(userChoice) {
						case 1:
							System.out.println("Saisissez le montant à verser sur le compte.");
							double amountToAdd = scan.nextDouble();
							bankJob.pay(accountTyped, amountToAdd);
							break;
						case 2:
							System.out.println("Saisissez le montant à retirer sur ce compte.");
							double amountToWithdraw = scan.nextDouble();
							bankJob.withdraw(accountTyped, amountToWithdraw);
							break;
						case 3:
							System.out.println("Saisissez le numéro du compte destinataire");
							int targetAccount = scan.nextInt();
							System.out.println("Saisissez le montant à virer sur ce compte.");
							double amountToTransfer = scan.nextDouble();
							bankJob.transfert(accountTyped, targetAccount, amountToTransfer);
							break;
						case 4:
							System.out.println(bankJob.consultAccount(accountTyped));
							break;
						case 5:
							System.out.println(accountTyped);
							System.out.println(bankJob.listTransactions(accountTyped));
							break;
						case 6:
							System.out.println("Merci de votre visite, le programme va fermer.");
							break;
						default:
							System.out.println("Ce menu n'existe pas.");
						}
					} catch (InputMismatchException e) {
						System.out.println("Mauvaise saisie du menu.");
					}

				}//end while

			} catch (NullPointerException e) {
				System.out.println("Ce compte n'existe pas" );
			}catch (InputMismatchException e) {
				System.out.println("Mauvaise saisie du menu.");
			}
		}

		scan.close();

	}//end main

	private static void displayMenu() {
		System.out.println("---------------------------Tapez le numéro correspondant --------------------"
				+ "\n- 1 : versement"
				+ "\n- 2 : retrait"
				+ "\n- 3 : virement"
				+ "\n- 4 : informations sur ce compte"
				+ "\n- 5 : liste des opérations"
				+ "\n- 6 : sortir");
	}
}
