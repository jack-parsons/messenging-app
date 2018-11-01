package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Takes inputs from the user and uses it to manipulate the ClientModel
 * @author Jack
 *
 */
public class ClientController {
	private ClientView clientView;
	private ClientModel clientModel;

	public ClientController(ClientModel clientModel, ClientView clientView) {
		this.clientModel = clientModel;
		this.clientView = clientView;
		initModel();
	}
	
	/**
	 * Add the ActionListeners to the ClientView elements
	 */
	public void initController() {
		clientView.getSendButton().addActionListener(e -> sendButtonEvent(e));
		clientView.getSendTextField().addActionListener(e -> sendButtonEvent(e));
		clientView.getNewConnectionButton().addActionListener(e -> newConnectionButton(e));
	}
	
	public void initModel() {
		clientModel.setMessageDocument(clientView.getMessagePane().getDocument());
		clientModel.setSendDocument(clientView.getSendTextField().getDocument());
		clientModel.setPortDocument(clientView.getPortNumberTextField().getDocument());
		clientModel.setHostNameDocument(clientView.getHostNameTextField().getDocument());
	}
	
	public void sendButtonEvent(ActionEvent e) {
		clientModel.sendMessage();
	}
	
	public void newConnectionButton(ActionEvent e) {
		if (clientModel.connectToMessageServer()) {
			// If the connection succeeds
			clientView.showSucessfulConnectionDialogue();

			MessageEvent event = new MessageEvent() {
				public void sendMessage(String message) {
					clientModel.addMessage(message);
				}
			};
			new Thread(new ClientThread(clientModel.getSocket(), event)).start();
		} else {
			// If the connection fails
			clientView.showFailedConnectionDialogue();
		}
		
	}

}
