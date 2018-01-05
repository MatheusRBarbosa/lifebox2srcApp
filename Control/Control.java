package Control;

import Model.Model;

public class Control implements Runnable {

    int aux;
    public byte[] buffer = new byte[20];
    String tempC;
    String tempD;

    Model model = new Model();
    DaoController daoControl = new DaoController(model.identificacao);

    public void run() {
    /*Método que comunica com o arduino enviando e recebendo dados */
        while (true) {
            try {
                model.out.write('T'); // Enviado sinal para arduino retornar a string com temperatura
                Thread.sleep(3000); //Esperando 3s para dar tempo do arduino responder
                aux = model.in.read(buffer); //recebendo string do arduino e salvando no buffer
                String str = new String(buffer, "UTF-8");//Transformando buffer em string
                tempC = str.substring(0, 5);//Separando o valor do termometro C
                tempD = str.substring(8, 13);//Separando o valor do termometro D

                System.out.println("tempC: " + tempC);
                System.out.println("tempD: " + tempD);
                System.out.println("ID: " + model.identificacao);
                if (model.sendData) {
                    daoControl.timecontrol(tempC, tempD, model.identificacao);
                }
                model.setLabelTemp(tempC, tempD);
               
            } catch (Exception e1) {
            }
        }
    }
}
