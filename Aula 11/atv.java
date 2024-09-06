import java.util.Random;

class No {
    int chave, altura;
    No esquerdo, direito;

    No(int chave) {
        this.chave = chave;
        this.altura = 1;
    }
}

class ArvoreAVL {

    No raiz;

    // Método para obter a altura de um nó
    int obterAltura(No no) {
        return (no == null) ? 0 : no.altura;
    }

    // Método para calcular o fator de balanceamento de um nó
    int calcularBalanceamento(No no) {
        return (no == null) ? 0 : obterAltura(no.esquerdo) - obterAltura(no.direito);
    }

    // Realiza rotação à direita
    No rotacaoDireita(No y) {
        No x = y.esquerdo;
        No T2 = x.direito;

        // Executa a rotação
        x.direito = y;
        y.esquerdo = T2;

        // Atualiza as alturas
        y.altura = Math.max(obterAltura(y.esquerdo), obterAltura(y.direito)) + 1;
        x.altura = Math.max(obterAltura(x.esquerdo), obterAltura(x.direito)) + 1;

        // Retorna a nova raiz
        return x;
    }

    // Realiza rotação à esquerda
    No rotacaoEsquerda(No x) {
        No y = x.direito;
        No T2 = y.esquerdo;

        // Executa a rotação
        y.esquerdo = x;
        x.direito = T2;

        // Atualiza as alturas
        x.altura = Math.max(obterAltura(x.esquerdo), obterAltura(x.direito)) + 1;
        y.altura = Math.max(obterAltura(y.esquerdo), obterAltura(y.direito)) + 1;

        // Retorna a nova raiz
        return y;
    }

    // Método para inserir uma nova chave na árvore AVL
    No inserir(No no, int chave) {
        if (no == null)
            return new No(chave);

        if (chave < no.chave)
            no.esquerdo = inserir(no.esquerdo, chave);
        else if (chave > no.chave)
            no.direito = inserir(no.direito, chave);
        else
            return no;

        no.altura = 1 + Math.max(obterAltura(no.esquerdo), obterAltura(no.direito));

        int balanceamento = calcularBalanceamento(no);

        // Rotação à direita
        if (balanceamento > 1 && chave < no.esquerdo.chave)
            return rotacaoDireita(no);

        // Rotação à esquerda
        if (balanceamento < -1 && chave > no.direito.chave)
            return rotacaoEsquerda(no);

        // Rotação dupla (esquerda e direita)
        if (balanceamento > 1 && chave > no.esquerdo.chave) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }

        // Rotação dupla (direita e esquerda)
        if (balanceamento < -1 && chave < no.direito.chave) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    // Método para encontrar o nó com o menor valor
    No menorValorNo(No no) {
        No atual = no;
        while (atual.esquerdo != null)
            atual = atual.esquerdo;
        return atual;
    }

    // Método para remover uma chave da árvore AVL
    No removerNo(No raiz, int chave) {
        if (raiz == null)
            return raiz;

        if (chave < raiz.chave)
            raiz.esquerdo = removerNo(raiz.esquerdo, chave);
        else if (chave > raiz.chave)
            raiz.direito = removerNo(raiz.direito, chave);
        else {
            if ((raiz.esquerdo == null) || (raiz.direito == null)) {
                No temp = (raiz.esquerdo != null) ? raiz.esquerdo : raiz.direito;
                raiz = (temp == null) ? null : temp;
            } else {
                No temp = menorValorNo(raiz.direito);
                raiz.chave = temp.chave;
                raiz.direito = removerNo(raiz.direito, temp.chave);
            }
        }

        if (raiz == null)
            return raiz;

        raiz.altura = Math.max(obterAltura(raiz.esquerdo), obterAltura(raiz.direito)) + 1;

        int balanceamento = calcularBalanceamento(raiz);

        if (balanceamento > 1 && calcularBalanceamento(raiz.esquerdo) >= 0)
            return rotacaoDireita(raiz);

        if (balanceamento > 1 && calcularBalanceamento(raiz.esquerdo) < 0) {
            raiz.esquerdo = rotacaoEsquerda(raiz.esquerdo);
            return rotacaoDireita(raiz);
        }

        if (balanceamento < -1 && calcularBalanceamento(raiz.direito) <= 0)
            return rotacaoEsquerda(raiz);

        if (balanceamento < -1 && calcularBalanceamento(raiz.direito) > 0) {
            raiz.direito = rotacaoDireita(raiz.direito);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    // Método para exibir a árvore em pré-ordem, mostrando o fator de balanceamento
    void exibirPreOrdem(No no) {
        if (no != null) {
            System.out.print("Nó: " + no.chave + ", Fator de Balanceamento: " + calcularBalanceamento(no) + "\n");
            exibirPreOrdem(no.esquerdo);
            exibirPreOrdem(no.direito);
        }
    }
}

public class atv {
    public static void main(String[] args) {
        Random random = new Random();
        ArvoreAVL arvore = new ArvoreAVL();

        // Inserção de 100 números aleatórios
        for (int i = 0; i < 100; i++) {
            int numero = random.nextInt(1001) - 500; // Gera números entre -500 e 500
            arvore.raiz = arvore.inserir(arvore.raiz, numero);
        }

        // Exibição da árvore AVL após inserção
        System.out.println("Árvore AVL após inserção:");
        arvore.exibirPreOrdem(arvore.raiz);

        // Remoção de 20 números aleatórios
        for (int i = 0; i < 20; i++) {
            int numero = random.nextInt(1001) - 500;
            arvore.raiz = arvore.removerNo(arvore.raiz, numero);
        }

        // Exibição da árvore AVL após remoção
        System.out.println("\nÁrvore AVL após remoção:");
        arvore.exibirPreOrdem(arvore.raiz);
    }
}
