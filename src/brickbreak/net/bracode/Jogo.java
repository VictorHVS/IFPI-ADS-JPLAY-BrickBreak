package brickbreak.net.bracode;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Window;

public class Jogo {
	
	Window			janela;
	GameImage		fundo;
	Keyboard		teclado;
	Barra			barra;
	Bola			bola;
	ArrayList<Bloco> 	blocos;
	Integer[] blocos2 = new Integer[2]; 
	Font			fonte;
	
	boolean			executando;
	boolean			pontua;
	int				vida;
	int				pontos;
	long			velocidade;
	
	public Jogo() {
		inicializa();
		loop();
	}
	//todas as inicializacoes
	public void inicializa(){
		executando	= true;
		janela		= new Window(800, 600);
		teclado		= janela.getKeyboard();
		fundo		= new GameImage("resources/imagens/fundo.png");
		barra		= new Barra("resources/imagens/anibarra.png", 2);
		bola		= new Bola("resources/imagens/anibola.png", 7, true, true);
		blocos		= new ArrayList<>();
		fonte		= new Font("Arial", Font.BOLD, 20);
		pontua		= false;
		
		vida 		= 4;
		pontos		= 0;
		velocidade	= 10;
		
		barra.setSequence(0, 1);
		barra.x 	= fundo.width/2 - barra.width/2;
		barra.y		= 600 - barra.height;
		bola.setTotalDuration(300);
		bola.x	 	= fundo.width/2 - bola.width/7;
		bola.y		= 500;
		//instancio e inicializo todos os blocos
		int linha = 1;
		int coluna = 1;
		
		for (int i = 0; i < 80; i++) {
			
			blocos.add(new Bloco("resources/imagens/bloco.png"));
			
			blocos.get(i).x = coluna * 70;
			blocos.get(i).y = 70 + (linha * 40);
			
			coluna++;
			
			if(coluna % 11 == 0){
				coluna = 1;
				linha++;
			}
		}
	}
	
	public void desenha(){

		fundo.draw();
		barra.draw();
		bola.draw();
		bola.update();
		for(Bloco bloco : blocos){
			bloco.draw();
		}
		janela.drawText(Integer.toString(vida), 120, 35, Color.black, fonte);
		janela.drawText(Integer.toString(pontos), 610, 35, Color.black, fonte);
	}
	
	public void loop(){
		while(executando){
			desenha();
					
			//movimentos
			barra.moveX(10);
			bola.moverX(6);
			bola.moverY(6);
			
			//verifico colisoes
			for(int i = 0; i < blocos.size(); i++){
				if(bola.collided(blocos.get(i))){
					blocos.remove(i);
					
					pontos++;
					// Altero o movimento das
					if (bola.moverY) {
						bola.moverY = false;
					} else {
						bola.moverY = true;
					}
					break;
				}
			}
			if(barra.collided(bola)){
				bola.moverY = false;
			}
			
			//configuracoes de vida
			if(bola.y >= 600){
				vida--;
				bola.y = 500;
				bola.x	 	= fundo.width/2 - bola.width/7;
				bola.moverY = false;
				barra.x = fundo.width/2 - barra.width/2;
			}
			if(vida < 3){
				barra.setSequence(1, 2);
			}
			else{
				barra.setSequence(0, 1);
			}
			
			if(pontua == false && pontos % 20 == 0){
				vida++;
				pontua = true;
			}else if(pontos % 20 == 1){
				pontua = false;
			}
			
			//uso do teclado
			if(teclado.keyDown(teclado.ESCAPE_KEY) || vida == 0 || blocos.size() == 0){
				executando = false;
			}
						
			janela.delay(velocidade);
			janela.update();
		}
		
		janela.exit();
	}
}