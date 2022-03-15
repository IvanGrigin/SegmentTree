public class Tree {

    public int n;
    public int[] a;     //массив
    public int tree[];  //дерево отрезков

    //создание массива и дерева отрезков
    public void first_add(int n){
        this.n = n;
        a = new int[n];
        tree = new int[4*n];
    }
    //Построение дерева по массиву
    public void build_tree(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = a[tl];    //сумма отрезка из одного элемента равна этому элементу
        } else {
            //tm - элемент половины отрезка.
            //отрезок разбивается на два отрезка [tl; tm], [tm + 1; tr]
            int tm = (tl + tr) / 2;
            build_tree(v * 2, tl, tm);
            build_tree(v * 2 + 1, tm + 1, tr);
            tree[v] = tree[v * 2] + tree[v * 2 + 1];
            // * Здесь могло быть любое другое условие, например Math.max()
        }
    }

    //Запрос суммы
    //l, r - границы запроса
    public int get_sum(int l, int r) {
        return get_sum(l, r, 1, 0, n-1);
    }
    public int get_sum(int l, int r, int v, int tl, int tr) {
        if (l <= tl && tr <= r) {
            return tree[v];
        }

        if (tr < l || r < tl) {
            return 0;
        }

        int tm = (tl + tr) / 2;
        int sum = get_sum(l, r, v * 2, tl, tm) + get_sum(l, r, v * 2 + 1, tm + 1, tr);
        // * Здесь могло быть любое другое условие, например Math.max()

        return sum;
    }

    //Измениение элемента по индексу
    //i - индекс элемента, val - новое значение
    //tl, tr - границы отрезка изменений
    public void set(int i, int val, int v, int tl, int tr) {
        if (i <= tl && tr <= i) {    //i = tl = tr
            a[i] = val;
            tree[v] = val;
        } else if (tr < i || i < tl) {
        // Ничего не делать, так как нечего изменять
        } else {
            int tm = (tl + tr) / 2;
            set(i, val, v * 2, tl, tm);
            set(i, val, v * 2 + 1, tm + 1, tr);

            tree[v] = tree[v * 2] + tree[v * 2 + 1];
            // * Здесь могло быть любое другое условие, например Math.max()
        }
    }
}

class Main_Tree {
    public static void main(String[] args) {
        Tree tree = new Tree();
        int n = 4;
        tree.first_add(n);
        for (int i = 0; i < tree.a.length; i++) {
            tree.a[i] = (int) ((Math.random() - 0.5) * 2000);
            System.out.println(tree.a[i]);
            // Создание массива произвольных чисел
        }

        tree.build_tree(1, 0, n - 1);
        
        System.out.println(tree.get_sum(0,n-1));
    }
}
