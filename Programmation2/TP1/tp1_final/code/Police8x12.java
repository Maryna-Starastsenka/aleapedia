/**
 * Données graphiques pour une police monospace des 256 premiers
 * caractères Unicode.
 */
public class Police8x12 {

    // Nombre de colonnes dans l'image d'un caractère
    public static final int LARGEUR = 8;

    // Nombre de rangées dans l'image d'un caractère
    public static final int HAUTEUR = 12;

    //Constructeur vide:
    //public Police8x12() {}

    /**
     * Tableau de 256 éléments. Chaque élément décrit l'image d'un caractère.
     *
     * L'encodage des images de caractères est le suivant. L'image d'un
     * caractère est donné par un tableau contenant un nombre d'entiers égal à
     * hauteur (par exemple dans une police 8 par 12, il y aura 12 entiers dans
     * le tableau). L'encodage binaire de cet entier donne la composition d'une
     * rangée de l'image. Chaque bit à 1 dans l'encodage binaire correspond à un
     * pixel coloré de l'image. Le nombre de bits significatifs par entier est
     * égal à largeur.
     *
     * Par exemple, dans la police ci-dessous, le caractère B, qui a le code 66
     * dans le jeu de caractères Unicode, a une image décrite par ce tableau de
     * 12 bytes :
     *
     * {0x00,0x7c,0x22,0x22,0x22,0x3c,0x22,0x22,0x22,0x7c,0x00,0x00}
     *
     * Si on encode en binaire chacun de ces entiers et qu'on remplace ensuite
     * parmi les 8 bits de poids faible chaque 0 par un "." et chaque 1 par "#"
     * pour obtenir le patron de pixels on obtient :
     *
     * 0x00 est encodé par 00000000 ce qui donne le patron ........
     * 0x7c est encodé par 01111100 ce qui donne le patron .#####..
     * 0x22 est encodé par 00100010 ce qui donne le patron ..#...#.
     * 0x22 est encodé par 00100010 ce qui donne le patron ..#...#.
     * 0x22 est encodé par 00100010 ce qui donne le patron ..#...#.
     * 0x3c est encodé par 00111100 ce qui donne le patron ..####..
     * 0x22 est encodé par 00100010 ce qui donne le patron ..#...#.
     * 0x22 est encodé par 00100010 ce qui donne le patron ..#...#.
     * 0x22 est encodé par 00100010 ce qui donne le patron ..#...#.
     * 0x7c est encodé par 01111100 ce qui donne le patron .#####..
     * 0x00 est encodé par 00000000 ce qui donne le patron ........
     * 0x00 est encodé par 00000000 ce qui donne le patron ........
     */
    private static byte[][] images = {
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 0
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 1
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 2
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 3
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 4
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 5
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 6
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 7
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 8
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 9
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 10
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 11
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 12
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 13
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 14
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 15
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 16
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 17
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 18
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 19
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 20
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 21
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 22
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 23
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 24
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 25
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 26
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 27
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 28
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 29
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 30
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 31
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 32
        {0x00,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x00,0x08,0x00,0x00}, // 33 !
        {0x00,0x14,0x14,0x14,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 34 "
        {0x00,0x00,0x14,0x14,0x3e,0x14,0x3e,0x14,0x14,0x00,0x00,0x00}, // 35 #
        {0x00,0x00,0x08,0x1e,0x28,0x1c,0x0a,0x3c,0x08,0x00,0x00,0x00}, // 36 $
        {0x00,0x22,0x52,0x24,0x08,0x08,0x10,0x24,0x4a,0x44,0x00,0x00}, // 37 %
        {0x00,0x00,0x00,0x30,0x48,0x48,0x30,0x4a,0x44,0x3a,0x00,0x00}, // 38 &
        {0x00,0x08,0x08,0x08,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 39 '
        {0x00,0x04,0x08,0x08,0x10,0x10,0x10,0x08,0x08,0x04,0x00,0x00}, // 40 (
        {0x00,0x10,0x08,0x08,0x04,0x04,0x04,0x08,0x08,0x10,0x00,0x00}, // 41 )
        {0x00,0x00,0x00,0x24,0x18,0x7e,0x18,0x24,0x00,0x00,0x00,0x00}, // 42 *
        {0x00,0x00,0x00,0x08,0x08,0x3e,0x08,0x08,0x00,0x00,0x00,0x00}, // 43 +
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x1c,0x18,0x20,0x00}, // 44 ,
        {0x00,0x00,0x00,0x00,0x00,0x3e,0x00,0x00,0x00,0x00,0x00,0x00}, // 45 -
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x08,0x1c,0x08,0x00}, // 46 .
        {0x00,0x02,0x02,0x04,0x04,0x08,0x10,0x10,0x20,0x20,0x00,0x00}, // 47 /
        {0x00,0x18,0x24,0x42,0x42,0x42,0x42,0x42,0x24,0x18,0x00,0x00}, // 48 0
        {0x00,0x08,0x18,0x28,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 49 1
        {0x00,0x3c,0x42,0x42,0x02,0x04,0x18,0x20,0x40,0x7e,0x00,0x00}, // 50 2
        {0x00,0x7e,0x02,0x04,0x08,0x1c,0x02,0x02,0x42,0x3c,0x00,0x00}, // 51 3
        {0x00,0x04,0x0c,0x14,0x24,0x44,0x44,0x7e,0x04,0x04,0x00,0x00}, // 52 4
        {0x00,0x7e,0x40,0x40,0x5c,0x62,0x02,0x02,0x42,0x3c,0x00,0x00}, // 53 5
        {0x00,0x1c,0x20,0x40,0x40,0x5c,0x62,0x42,0x42,0x3c,0x00,0x00}, // 54 6
        {0x00,0x7e,0x02,0x04,0x08,0x08,0x10,0x10,0x20,0x20,0x00,0x00}, // 55 7
        {0x00,0x3c,0x42,0x42,0x42,0x3c,0x42,0x42,0x42,0x3c,0x00,0x00}, // 56 8
        {0x00,0x3c,0x42,0x42,0x46,0x3a,0x02,0x02,0x04,0x38,0x00,0x00}, // 57 9
        {0x00,0x00,0x00,0x08,0x1c,0x08,0x00,0x00,0x08,0x1c,0x08,0x00}, // 58 :
        {0x00,0x00,0x00,0x08,0x1c,0x08,0x00,0x00,0x1c,0x18,0x20,0x00}, // 59 ;
        {0x00,0x02,0x04,0x08,0x10,0x20,0x10,0x08,0x04,0x02,0x00,0x00}, // 60 <
        {0x00,0x00,0x00,0x00,0x7e,0x00,0x00,0x7e,0x00,0x00,0x00,0x00}, // 61 =
        {0x00,0x20,0x10,0x08,0x04,0x02,0x04,0x08,0x10,0x20,0x00,0x00}, // 62 >
        {0x00,0x3c,0x42,0x42,0x02,0x04,0x08,0x08,0x00,0x08,0x00,0x00}, // 63 ?
        {0x00,0x3c,0x42,0x42,0x4e,0x52,0x56,0x4a,0x40,0x3c,0x00,0x00}, // 64 @
        {0x00,0x18,0x24,0x42,0x42,0x42,0x7e,0x42,0x42,0x42,0x00,0x00}, // 65 A
        {0x00,0x7c,0x22,0x22,0x22,0x3c,0x22,0x22,0x22,0x7c,0x00,0x00}, // 66 B
        {0x00,0x3c,0x42,0x40,0x40,0x40,0x40,0x40,0x42,0x3c,0x00,0x00}, // 67 C
        {0x00,0x7c,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x7c,0x00,0x00}, // 68 D
        {0x00,0x7e,0x40,0x40,0x40,0x78,0x40,0x40,0x40,0x7e,0x00,0x00}, // 69 E
        {0x00,0x7e,0x40,0x40,0x40,0x78,0x40,0x40,0x40,0x40,0x00,0x00}, // 70 F
        {0x00,0x3c,0x42,0x40,0x40,0x40,0x4e,0x42,0x46,0x3a,0x00,0x00}, // 71 G
        {0x00,0x42,0x42,0x42,0x42,0x7e,0x42,0x42,0x42,0x42,0x00,0x00}, // 72 H
        {0x00,0x3e,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 73 I
        {0x00,0x0e,0x04,0x04,0x04,0x04,0x04,0x04,0x44,0x38,0x00,0x00}, // 74 J
        {0x00,0x42,0x44,0x48,0x50,0x60,0x50,0x48,0x44,0x42,0x00,0x00}, // 75 K
        {0x00,0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x7e,0x00,0x00}, // 76 L
        {0x00,0x42,0x66,0x66,0x5a,0x5a,0x42,0x42,0x42,0x42,0x00,0x00}, // 77 M
        {0x00,0x42,0x42,0x62,0x52,0x4a,0x46,0x42,0x42,0x42,0x00,0x00}, // 78 N
        {0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 79 O
        {0x00,0x7c,0x42,0x42,0x42,0x7c,0x40,0x40,0x40,0x40,0x00,0x00}, // 80 P
        {0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x52,0x4a,0x3c,0x02,0x00}, // 81 Q
        {0x00,0x7c,0x42,0x42,0x42,0x7c,0x50,0x48,0x44,0x42,0x00,0x00}, // 82 R
        {0x00,0x3c,0x42,0x40,0x40,0x3c,0x02,0x02,0x42,0x3c,0x00,0x00}, // 83 S
        {0x00,0x3e,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x00,0x00}, // 84 T
        {0x00,0x42,0x42,0x42,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 85 U
        {0x00,0x42,0x42,0x42,0x24,0x24,0x24,0x18,0x18,0x18,0x00,0x00}, // 86 V
        {0x00,0x42,0x42,0x42,0x42,0x5a,0x5a,0x66,0x66,0x42,0x00,0x00}, // 87 W
        {0x00,0x42,0x42,0x24,0x24,0x18,0x24,0x24,0x42,0x42,0x00,0x00}, // 88 X
        {0x00,0x22,0x22,0x14,0x14,0x08,0x08,0x08,0x08,0x08,0x00,0x00}, // 89 Y
        {0x00,0x7e,0x02,0x04,0x08,0x18,0x10,0x20,0x40,0x7e,0x00,0x00}, // 90 Z
        {0x3c,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x3c,0x00}, // 91 [
        {0x00,0x20,0x20,0x10,0x10,0x08,0x04,0x04,0x02,0x02,0x00,0x00}, // 92 \
        {0x3c,0x04,0x04,0x04,0x04,0x04,0x04,0x04,0x04,0x04,0x3c,0x00}, // 93 ]
        {0x00,0x08,0x14,0x22,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 94 ^
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x7e,0x00}, // 95 _
        {0x10,0x08,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 96 `
        {0x00,0x00,0x00,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 97 a
        {0x00,0x40,0x40,0x40,0x5c,0x62,0x42,0x42,0x62,0x5c,0x00,0x00}, // 98 b
        {0x00,0x00,0x00,0x00,0x3c,0x42,0x40,0x40,0x42,0x3c,0x00,0x00}, // 99 c
        {0x00,0x02,0x02,0x02,0x3a,0x46,0x42,0x42,0x46,0x3a,0x00,0x00}, // 100 d
        {0x00,0x00,0x00,0x00,0x3c,0x42,0x7e,0x40,0x42,0x3c,0x00,0x00}, // 101 e
        {0x00,0x1c,0x22,0x20,0x20,0x78,0x20,0x20,0x20,0x20,0x00,0x00}, // 102 f
        {0x00,0x00,0x00,0x00,0x3a,0x44,0x44,0x38,0x40,0x3c,0x42,0x3c}, // 103 g
        {0x00,0x40,0x40,0x40,0x5c,0x62,0x42,0x42,0x42,0x42,0x00,0x00}, // 104 h
        {0x00,0x00,0x08,0x00,0x18,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 105 i
        {0x00,0x00,0x02,0x00,0x06,0x02,0x02,0x02,0x02,0x22,0x22,0x1c}, // 106 j
        {0x00,0x40,0x40,0x40,0x44,0x48,0x70,0x48,0x44,0x42,0x00,0x00}, // 107 k
        {0x00,0x18,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 108 l
        {0x00,0x00,0x00,0x00,0x34,0x2a,0x2a,0x2a,0x2a,0x22,0x00,0x00}, // 109 m
        {0x00,0x00,0x00,0x00,0x5c,0x62,0x42,0x42,0x42,0x42,0x00,0x00}, // 110 n
        {0x00,0x00,0x00,0x00,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 111 o
        {0x00,0x00,0x00,0x00,0x5c,0x62,0x42,0x62,0x5c,0x40,0x40,0x40}, // 112 p
        {0x00,0x00,0x00,0x00,0x3a,0x46,0x42,0x46,0x3a,0x02,0x02,0x02}, // 113 q
        {0x00,0x00,0x00,0x00,0x5c,0x22,0x20,0x20,0x20,0x20,0x00,0x00}, // 114 r
        {0x00,0x00,0x00,0x00,0x3c,0x42,0x30,0x0c,0x42,0x3c,0x00,0x00}, // 115 s
        {0x00,0x00,0x20,0x20,0x78,0x20,0x20,0x20,0x22,0x1c,0x00,0x00}, // 116 t
        {0x00,0x00,0x00,0x00,0x42,0x42,0x42,0x42,0x46,0x3a,0x00,0x00}, // 117 u
        {0x00,0x00,0x00,0x00,0x22,0x22,0x22,0x14,0x14,0x08,0x00,0x00}, // 118 v
        {0x00,0x00,0x00,0x00,0x22,0x22,0x2a,0x2a,0x2a,0x14,0x00,0x00}, // 119 w
        {0x00,0x00,0x00,0x00,0x42,0x24,0x18,0x18,0x24,0x42,0x00,0x00}, // 120 x
        {0x00,0x00,0x00,0x00,0x42,0x42,0x42,0x46,0x3a,0x02,0x42,0x3c}, // 121 y
        {0x00,0x00,0x00,0x00,0x7e,0x04,0x08,0x10,0x20,0x7e,0x00,0x00}, // 122 z
        {0x0e,0x10,0x10,0x10,0x08,0x30,0x08,0x10,0x10,0x10,0x0e,0x00}, // 123 {
        {0x00,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x00,0x00}, // 124 |
        {0x38,0x04,0x04,0x04,0x08,0x06,0x08,0x04,0x04,0x04,0x38,0x00}, // 125 }
        {0x00,0x12,0x2a,0x24,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 126 ~
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 127
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 128
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 129
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 130
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 131
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 132
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 133
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 134
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 135
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 136
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 137
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 138
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 139
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 140
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 141
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 142
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 143
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 144
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 145
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 146
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 147
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 148
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 149
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 150
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 151
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 152
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 153
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 154
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 155
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 156
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 157
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 158
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 159
        {0x00,0x56,0x00,0x42,0x00,0x42,0x00,0x42,0x00,0x6a,0x00,0x00}, // 160
        {0x00,0x08,0x00,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x00,0x00}, // 161
        {0x00,0x08,0x1c,0x2a,0x28,0x28,0x2a,0x1c,0x08,0x00,0x00,0x00}, // 162
        {0x00,0x1c,0x22,0x20,0x20,0x70,0x20,0x20,0x22,0x5c,0x00,0x00}, // 163
        {0x00,0x00,0x00,0x42,0x3c,0x24,0x24,0x3c,0x42,0x00,0x00,0x00}, // 164
        {0x00,0x44,0x44,0x28,0x28,0x7c,0x10,0x7c,0x10,0x10,0x00,0x00}, // 165
        {0x00,0x08,0x08,0x08,0x08,0x00,0x08,0x08,0x08,0x08,0x00,0x00}, // 166
        {0x18,0x24,0x20,0x18,0x24,0x24,0x18,0x04,0x24,0x18,0x00,0x00}, // 167
        {0x00,0x24,0x24,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 168
        {0x00,0x3c,0x42,0x5a,0x52,0x52,0x52,0x5a,0x42,0x3c,0x00,0x00}, // 169
        {0x00,0x1c,0x02,0x1e,0x22,0x1e,0x00,0x3e,0x00,0x00,0x00,0x00}, // 170
        {0x00,0x00,0x0a,0x14,0x28,0x50,0x28,0x14,0x0a,0x00,0x00,0x00}, // 171
        {0x00,0x00,0x00,0x00,0x00,0x3e,0x02,0x02,0x00,0x00,0x00,0x00}, // 172
        {0x00,0x00,0x00,0x00,0x00,0x3c,0x00,0x00,0x00,0x00,0x00,0x00}, // 173
        {0x00,0x3c,0x42,0x5a,0x56,0x56,0x5a,0x56,0x42,0x3c,0x00,0x00}, // 174
        {0x00,0x3e,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 175
        {0x00,0x18,0x24,0x24,0x18,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 176
        {0x00,0x00,0x08,0x08,0x3e,0x08,0x08,0x00,0x3e,0x00,0x00,0x00}, // 177
        {0x10,0x28,0x08,0x10,0x20,0x38,0x00,0x00,0x00,0x00,0x00,0x00}, // 178
        {0x38,0x08,0x10,0x08,0x28,0x10,0x00,0x00,0x00,0x00,0x00,0x00}, // 179
        {0x08,0x10,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}, // 180
        {0x00,0x00,0x00,0x00,0x42,0x42,0x42,0x42,0x66,0x5a,0x40,0x00}, // 181
        {0x00,0x3e,0x74,0x74,0x74,0x34,0x14,0x14,0x14,0x14,0x00,0x00}, // 182
        {0x00,0x00,0x00,0x00,0x00,0x18,0x00,0x00,0x00,0x00,0x00,0x00}, // 183
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x08,0x10}, // 184
        {0x10,0x30,0x10,0x10,0x10,0x38,0x00,0x00,0x00,0x00,0x00,0x00}, // 185
        {0x00,0x18,0x24,0x24,0x18,0x00,0x3c,0x00,0x00,0x00,0x00,0x00}, // 186
        {0x00,0x00,0x50,0x28,0x14,0x0a,0x14,0x28,0x50,0x00,0x00,0x00}, // 187
        {0x20,0x60,0x20,0x20,0x22,0x76,0x0a,0x0a,0x0e,0x02,0x00,0x00}, // 188
        {0x20,0x60,0x20,0x20,0x24,0x7a,0x02,0x04,0x08,0x0e,0x00,0x00}, // 189
        {0x70,0x10,0x20,0x10,0x52,0x26,0x0a,0x0a,0x0e,0x02,0x00,0x00}, // 190
        {0x00,0x10,0x00,0x10,0x10,0x20,0x40,0x42,0x42,0x3c,0x00,0x00}, // 191
        {0x10,0x08,0x00,0x18,0x24,0x42,0x42,0x7e,0x42,0x42,0x00,0x00}, // 192
        {0x08,0x10,0x00,0x18,0x24,0x42,0x42,0x7e,0x42,0x42,0x00,0x00}, // 193
        {0x18,0x24,0x00,0x18,0x24,0x42,0x42,0x7e,0x42,0x42,0x00,0x00}, // 194
        {0x32,0x4c,0x00,0x18,0x24,0x42,0x42,0x7e,0x42,0x42,0x00,0x00}, // 195
        {0x24,0x24,0x00,0x18,0x24,0x42,0x42,0x7e,0x42,0x42,0x00,0x00}, // 196
        {0x18,0x24,0x18,0x18,0x24,0x42,0x42,0x7e,0x42,0x42,0x00,0x00}, // 197
        {0x00,0x2e,0x50,0x50,0x50,0x5c,0x70,0x50,0x50,0x5e,0x00,0x00}, // 198
        {0x00,0x3c,0x42,0x40,0x40,0x40,0x40,0x40,0x42,0x3c,0x08,0x10}, // 199
        {0x10,0x08,0x00,0x7e,0x40,0x40,0x78,0x40,0x40,0x7e,0x00,0x00}, // 200
        {0x08,0x10,0x00,0x7e,0x40,0x40,0x78,0x40,0x40,0x7e,0x00,0x00}, // 201
        {0x18,0x24,0x00,0x7e,0x40,0x40,0x78,0x40,0x40,0x7e,0x00,0x00}, // 202
        {0x24,0x24,0x00,0x7e,0x40,0x40,0x78,0x40,0x40,0x7e,0x00,0x00}, // 203
        {0x10,0x08,0x00,0x3e,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 204
        {0x08,0x10,0x00,0x3e,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 205
        {0x08,0x14,0x00,0x3e,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 206
        {0x22,0x22,0x00,0x3e,0x08,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 207
        {0x00,0x7c,0x22,0x22,0x22,0x72,0x22,0x22,0x22,0x7c,0x00,0x00}, // 208
        {0x32,0x4c,0x00,0x42,0x62,0x52,0x52,0x4a,0x46,0x42,0x00,0x00}, // 209
        {0x10,0x08,0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 210
        {0x08,0x10,0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 211
        {0x18,0x24,0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 212
        {0x32,0x4c,0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 213
        {0x24,0x24,0x00,0x3c,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 214
        {0x00,0x00,0x00,0x42,0x24,0x18,0x18,0x24,0x42,0x00,0x00,0x00}, // 215
        {0x02,0x3c,0x46,0x4a,0x4a,0x52,0x52,0x52,0x62,0x3c,0x40,0x00}, // 216
        {0x10,0x08,0x00,0x42,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 217
        {0x08,0x10,0x00,0x42,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 218
        {0x18,0x24,0x00,0x42,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 219
        {0x24,0x24,0x00,0x42,0x42,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 220
        {0x04,0x08,0x00,0x22,0x22,0x14,0x08,0x08,0x08,0x08,0x00,0x00}, // 221
        {0x00,0x40,0x7c,0x42,0x42,0x42,0x7c,0x40,0x40,0x40,0x00,0x00}, // 222
        {0x00,0x18,0x24,0x24,0x28,0x28,0x24,0x22,0x22,0x2c,0x00,0x00}, // 223
        {0x00,0x10,0x08,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 224
        {0x00,0x08,0x10,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 225
        {0x00,0x18,0x24,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 226
        {0x00,0x32,0x4c,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 227
        {0x00,0x24,0x24,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 228
        {0x18,0x24,0x18,0x00,0x3c,0x02,0x3e,0x42,0x46,0x3a,0x00,0x00}, // 229
        {0x00,0x00,0x00,0x00,0x34,0x0a,0x3e,0x48,0x4a,0x34,0x00,0x00}, // 230
        {0x00,0x00,0x00,0x00,0x3c,0x42,0x40,0x40,0x42,0x3c,0x08,0x10}, // 231
        {0x00,0x10,0x08,0x00,0x3c,0x42,0x7e,0x40,0x42,0x3c,0x00,0x00}, // 232
        {0x00,0x08,0x10,0x00,0x3c,0x42,0x7e,0x40,0x42,0x3c,0x00,0x00}, // 233
        {0x00,0x18,0x24,0x00,0x3c,0x42,0x7e,0x40,0x42,0x3c,0x00,0x00}, // 234
        {0x00,0x24,0x24,0x00,0x3c,0x42,0x7e,0x40,0x42,0x3c,0x00,0x00}, // 235
        {0x00,0x10,0x08,0x00,0x18,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 236
        {0x00,0x08,0x10,0x00,0x18,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 237
        {0x00,0x18,0x24,0x00,0x18,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 238
        {0x00,0x24,0x24,0x00,0x18,0x08,0x08,0x08,0x08,0x3e,0x00,0x00}, // 239
        {0x24,0x18,0x28,0x04,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 240
        {0x00,0x32,0x4c,0x00,0x5c,0x62,0x42,0x42,0x42,0x42,0x00,0x00}, // 241
        {0x00,0x10,0x08,0x00,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 242
        {0x00,0x08,0x10,0x00,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 243
        {0x00,0x18,0x24,0x00,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 244
        {0x00,0x32,0x4c,0x00,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 245
        {0x00,0x24,0x24,0x00,0x3c,0x42,0x42,0x42,0x42,0x3c,0x00,0x00}, // 246
        {0x00,0x00,0x08,0x08,0x00,0x3e,0x00,0x08,0x08,0x00,0x00,0x00}, // 247
        {0x00,0x00,0x00,0x02,0x3c,0x46,0x4a,0x52,0x62,0x3c,0x40,0x00}, // 248
        {0x00,0x10,0x08,0x00,0x42,0x42,0x42,0x42,0x46,0x3a,0x00,0x00}, // 249
        {0x00,0x08,0x10,0x00,0x42,0x42,0x42,0x42,0x46,0x3a,0x00,0x00}, // 250
        {0x00,0x18,0x24,0x00,0x42,0x42,0x42,0x42,0x46,0x3a,0x00,0x00}, // 251
        {0x00,0x24,0x24,0x00,0x42,0x42,0x42,0x42,0x46,0x3a,0x00,0x00}, // 252
        {0x00,0x08,0x10,0x00,0x42,0x42,0x42,0x46,0x3a,0x02,0x42,0x3c}, // 253
        {0x00,0x00,0x40,0x40,0x5c,0x62,0x42,0x42,0x62,0x5c,0x40,0x40}, // 254
        {0x00,0x24,0x24,0x00,0x42,0x42,0x42,0x46,0x3a,0x02,0x42,0x3c}  // 255
    };

    /**
     * Accès à l'image d'un des caractères
     */
    public static byte[] getImage(char code) {
        if(code < 0 || code > images.length) {
            // Retourne la représentation de ? si le caractère n'est
            // pas dans le range défini
            return images[63].clone();
        }

        return images[code].clone();
    }
}