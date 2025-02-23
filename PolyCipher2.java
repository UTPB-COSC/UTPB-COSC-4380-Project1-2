/* Polyalphabetic Cipher
 * Created by: Cameron Glenn, Johnny Ngo, and Kevin Franco
 * February 22, 2025
 * 
 * 
 * 
 */
import java.util.ArrayList;

public class PolyCipher2 extends Cipher {

    private final String key;
    private char[][] square;

    ////////////////////////////////////////////////////////////////////
    /// Constructors                                                  //
    ////////////////////////////////////////////////////////////////////
    
    // Our default constructor, accepts a key
    public PolyCipher2(String k) {
        this.key = k;
        this.alphabet = getAlphabet(new String[] {"lower"});
        this.square = new char[alphabet.size()][alphabet.size()];
    }

    // Can get different versions of the alphabet
    public PolyCipher2(String k, String[] names) {
        this.key = k;
        this.alphabet = getAlphabet(names);
        this.square = new char[alphabet.size()][alphabet.size()];
    }

    // Creates a PolyCipher2 with the passed through beta matrix char[][]
    public PolyCipher2(String k, char[][] beta) {
        this.key = k;
        this.alphabet = getAlphabet(new String[] {"lower"});
        this.square = beta;
    }

    ////////////////////////////////////////////////////////////////////
    /// Functions                                                     //
    ////////////////////////////////////////////////////////////////////
    
    /**
         * Encrypts plaintext using the Beta Matrix retrieved via getBeta().
         * 
         * @param plaintext The plaintext to be encrypted
         * @return The encrypted text, created using our key and matrix.
         */
    @Override
    public String encrypt(String plaintext) {
        String output = "";
        int squareCount = 0;
        for(char c : plaintext.toCharArray()) {
            int index = convert(c);
            output += square[index][squareCount];

            if(squareCount >= square.length)
                squareCount = 0;
        }
        return output;
    }

    @Override
    public String decrypt(String ciphertext) {
        return null;
    }

    public void generateSquare() {
        CaesarCipher generator = new CaesarCipher(0, alphabet);
        StringBuilder plaintext = new StringBuilder();
        for (char c : alphabet) {
            plaintext.append(c);
        }
        for (int row = 0; row < square.length; row++) {
            square[row] = generator.encrypt(plaintext.toString()).toCharArray();
            generator.setKey(row+1);
        }
    }

    public void scrambleSquare() {
        for(int row = 0; row < square.length * 10; row++) {
            int a = Rand.randInt(square.length);
            int b = Rand.randInt(square.length);
            char[] swap = square[a];
            square[a] = square[b];
            square[b] = swap;
        }
        for (int col = 0; col < square.length * 10; col++) {
            int a = Rand.randInt(square.length);
            int b = Rand.randInt(square.length);
            for (int row = 0; row < square.length; row++) {
                char c = square[row][a];
                square[row][a] = square[row][b];
                square[row][b] = c;
            }
        }
    }

    public static String generateKey(String plaintext, ArrayList<Character> alpha) {
        StringBuilder k = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            int idx = Rand.randInt(alpha.size());
            k.append(alpha.get(idx));
        }
        return k.toString();
    }

    public void printSquare() {
        for (int row = 0; row < square.length; row++) {
            for (int col = 0; col < square.length; col++) {
                System.out.print(square[row][col]);
            }
            System.out.println();
        }
    }

    /**
         * Converts a character into the required number for indexing our beta matrix. 
         * Uses the default alphabet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?'";:/
         * 
         * @param character The character to be converted to a number based on it's index in our default alphabet
         * @return The number representation of our character
         */
    public int convert(char character) {
        String defaultAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?'\";:/";
        return defaultAlphabet.indexOf(character);
    }

    /**
     * Retrieve the beta matrix from matrix.csv. Stores it to this.square
     */
    public void getBeta() {

    }

    /**
    * Writes the beta matrix from this.square to matrix.csv. Lines of the matrix should appear in the csv exactly as they do when printed.
    * (Same number of rows and columns, a square)
    */
    public void storeBeta() {
        String outputName = "matrix.csv"; // Name of the file that stores our random beta matrix

        
        for (int row = 0; row < square.length; row++) {
            char[] betaRow = this.square[row];
            System.out.println(betaRow);
        }
    }

    ////////////////////////////////////////////////////////////////////
    /// Main                                                          //
    ////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        String plaintext = "Old friend, I hope this missive finds you well. Though, as you are now essentially immortal, I would guess that wellness on your part is something of a given. I realize that you are probably still angry. That is pleasant to know. Much as your perpetual health, I have come to rely upon your dissatisfaction with me. It is one of the Cosmere's great constants, I should think. Let me first assure you that the element is quite safe. I have found a good home for it. I protect its safety like I protect my own skin, you might say. You do not agree with my quest. I understand that, so much as it is possible to understand someone with whom I disagree so completely. Might I be quite frank? Before, you asked why I was so concerned. It is for the following reason: Ati was once a kind and generous man, and you saw what became of him. Rayse, on the other hand, was among the most loathsome, crafty, and dangerous individuals I had ever met. He holds the most frightening and terrible of all the Shards. Ponder on that for a time, you old reptile, and tell me if your insistence on nonintervention holds firm. Because I assure you, Rayse will not be similarly inhibited. One need only look at the aftermath of his brief visit to Sel to see proof of what I say. In case you have turned a blind eye to that disaster, know that Aona and Skai are both dead, and that which they held has been Splintered. Presumably to prevent anyone from rising up to challenge Rayse. You have accused me of arrogance in my quest. You have accused me of perpetuating my grudge against Rayse and Bavadin. Both accusations are true. Neither point makes the things I have written to you untrue. I am being chased. Your friends of the Seventeenth Shard, I suspect. I believe they're still lost, following a false trail I left for them. They'll be happier that way. I doubt they have any inkling what to do with me should they actually catch me. If anything I have said makes a glimmer of sense to you, I trust that you'll call them off. Or maybe you could astound me and ask them to do something productive for once. For I have never been dedicated to a more important purpose, and the very pillars of the sky will shake with the results of our war here. I ask again. Support me. Do not stand aside and let disaster consume more lives. I've never begged you for something before, old friend. I do so now.";
        String key = "I write these words in steel, for anything not set in metal cannot be trusted. I have begun to wonder if I am the only sane man remaining. Can the others not see? They have been waiting so long for their hero to come - the one spoken of in Terris prophecies - that they quickly jump between conclusions, presuming that each story and legend applies to this one man. My brethren ignore the other facts. They cannot connect the other strange things that are happening. They are deaf to my objections and blind to my discoveries. Perhaps they are right. Perhaps I am mad, or jealous, or simply daft. My name is Kwaan. Philosopher, scholar, traitor. I am the one who discovered Alendi, and I am the one who first proclaimed him to be the Hero of Ages. I am the one who started this all. And I am the one who betrayed him, for I now know that he must never be allowed to complete his quest. I write this record now, pounding it into a metal slab, because I am afraid. Afraid for myself, yes—I admit to being human. If Alendi does return from the Well of Ascension, I am certain that my death will be one of his first objectives. He is not an evil man, but he is a ruthless one. That is, I think, a product of what he has been through. I am also afraid, however, that all I have known—that my story—will be forgotten. I am afraid for the world that is to come. Afraid that my plans will fail. Afraid of a doom worse, even, than the Deepness. It all comes back to poor Alendi. I feel bad for him, and for all the things he has been forced to endure. For what he has been forced to become. But, let me begin at the beginning. I met Alendi first in Khlennium; he was a young lad then, and had not yet been warped by a decade spent leading armies. Alendi's height struck me the first time I saw him. Here was a man who towered over others, a man who—despite his youth and his humble clothing—demanded respect. Oddly, it was Alendi's simple ingenuousness that first led me to befriend him. I employed him as an assistant during his first months in the grand city. It wasn't until years later that I became convinced that Alendi was the Hero of Ages. Hero of Ages: the one called Rabzeen in Khlennium, the Anamnesor. Savior. When I finally had the realization—finally connected all of the signs of the Anticipation to Alendi—I was so excited. Yet, when I announced my discovery to the other Worldbringers, I was met with scorn. Oh, how I wish that I had listened to them. And yet, any who know me will realize that there was no chance I would give up so easily. Once I find something to investigate, I become dogged in my pursuit. I had determined that Alendi was the Hero of Ages, and I intended to prove it. I should have bowed before the will of the others; I shouldn't have insisted on traveling with Alendi to witness his journeys. It was inevitable that Alendi himself would find out what I believed him to be. Yes, he was the one who fueled the rumors after that. I could never have done what he himself did, convincing and persuading the world that he was indeed the Hero. I don't know if he himself believed it, but he made others think that he must be the one. If only the Terris religion, and belief in the Anticipation, hadn't spread beyond our people. If only the Deepness hadn't come when it did, providing a threat that drove men to desperation both in action and belief. If only I had passed over Alendi when looking for an assistant, all those years ago. It wasn't until a few years later that I began to notice the signs. I knew the prophecies—I am a Terris Worldbringer, after all. And yet, not all of us are religious men; some, such as myself, are more interested in other topics. However, during my time with Alendi, I could not help but become more interested in the Anticipation. He seemed to fit the signs so well. He was born of a humble family, yet married the daughter of a king. He could trade words with the finest of philosophers, and had an impressive memory. Nearly as good, even, as my own. Yet, he was not argumentative. The Terris rejected him, but he came to lead them. He commanded kings, and though he sought no empire, he became greater than all who had come before. He fathered no children, yet all of the land became his progeny. He was forced into war by a misunderstanding—and always claimed he was no warrior—yet he came to fight as well as any man. He was no simple soldier. He was a force of leadership—a man that fate itself seemed to support. He left ruin in his wake, but it was forgotten. He created kingdoms, and then destroyed them as he made the world anew. There were other proofs to connect Alendi to the Hero of Ages. Smaller things, things that only one trained in the lore of the Anticipation would have noticed. The birthmark on his arm. The way his hair turned gray when he was barely twenty and five years of age. The way he spoke, the way he treated people, the way he ruled. He simply seemed to fit. But, I must continue with the sparsest of detail. Space is limited. The other Worldbringers must have thought themselves humble when they came to me, admitting that they had been wrong. Even then, I was beginning to doubt my original declaration. But, I was prideful. In the end, my pride may have doomed us all. I had never received much attention from my brethren; they thought that my work and my interests were unsuitable to a Worldbringer. The couldn't see how my work, studying nature instead of religion, benefited the people of the fourteen lands. As the one who found Alendi, however, I became someone important. Foremost among the Worldbringers. There was a place for me, in the lore of the Anticipation—I thought myself the Announcer, the prophet foretold to discover the Hero of Ages. Renouncing Alendi then would have been to renounce my new position, my acceptance, by the others. And so I did not. But I do so now. Let it be known that I, Kwaan, Worldbringer of Terris, am a fraud. Alendi was never the Hero of Ages. At best, I have amplified his virtues, creating a Hero where there was none. At worst, I fear that all we believe may have been corrupted. And so I come to the focus of my argument. I apologize. Even forcing my words into steel, sitting and scratching in this frozen cave, I am prone to ramble. This is the problem. Though I believed in Alendi at first, I later became suspicious. It seemed that he fit the signs, true. But, well, how can I explain this? Could it be that he fit them too well? I know your argument. We speak of the Anticipation, of things foretold, of promises made by our greatest prophets of old. Of course the Hero of Ages will fit the prophecies. He will fit them perfectly. That's the idea. And yet... something about all this seems so convenient. It feels almost as if we constructed a hero to fit our prophecies, rather than allowing one to arise naturally. This was the worry I had, the thing that should have given me pause when my brethren came to me, finally willing to believe. After that, I began to see other problems. Some of you may know of my fabled memory. It is true; I need not a Feruchemist's metalmind to memorize a sheet of words in an instant. And I tell you, call me daft, but the words of the prophecies are changing. The alterations are slight. Clever, even. A word here, a slight twist there. But the words on the pages are different from the ones in my memory. The other Worldbringers scoff at me, for they have their metalminds to prove to them that the books and prophecies have not changed. And so, this is the great declaration I must make. There is something—some force—that wants us to believe that the Hero of Ages has come, and that he must travel to the Well of Ascension. Something is making the prophecies change so that they refer to Alendi more perfectly. And whatever this power is, it can change words within a Feruchemist's metalmind. The others call me mad. As I have said, that may be true. But must not even a madman rely on his own mind, his own experience, rather than that of others? I know what I have memorized. I know what is now repeated by the other Worldbringers. The two are not the same. I sense a craftiness behind these changes, a manipulation subtle and brilliant. I have spent the last two years in exile, trying to decipher what the alterations could mean. I have come to only one conclusion. Something has taken control of our religion, something nefarious, something that cannot be trusted. It misleads, and it shadows. It uses Alendi to destroy, leading him along a path of death and sorrow. It is pulling him toward the Well of Ascension, where the millennial power has gathered. I can only guess that it sent the Deepness as a method of making mankind more desperate, of pushing us to do as it wills. The prophecies have changed. They now tell Alendi that he must give up the power once he takes it. This is not what was once implied by the texts—they were more vague. And yet, the new version seems to make it a moral imperative. The texts now outline a terrible consequence if the Hero of Ages takes the power for himself. Alendi believes as they do. He is a good man—despite it all, he is a good man. A sacrificing man. In truth, all of his actions—all of the deaths, destructions, and pains that he has caused—have hurt him deeply. All of these things were, in truth, a kind of sacrifice for him. He is accustomed to giving up his own will for the common good, as he sees it. I have no doubt that if Alendi reaches the Well of Ascension, he will take the power and then—in the name of the presumed greater good—will give it up. Give it away to this same force that has changed the texts. Give it up to this force of destruction that has brought him to war, that has tempted him to kill, that has craftily led him to the north. This thing wants the power held in the Well, and it has raped our religion's holiest tenets in order to get it. And so, I have made one final gamble. My pleas, my teachings, my objections, and even my treasons were all ineffectual. Alendi has other counselors now, ones who tell him what he wants to hear. I have a young nephew, one Rashek. He hates all of Khlennium with the passion of envious youth. He hates Alendi even more acutely—though the two have never met—for Rashek feels betrayed that one of our oppressors should have been chosen as the Hero of Ages. Alendi will need guides through the Terris Mountains. I have charged Rashek with making certain that he and his trusted friends are chosen as those guides. Rashek is to try and lead Alendi in the wrong direction, to dissuade him, discourage him, or otherwise foil his quest. Alendi doesn't know that he has been deceived, that we've all been deceived, and he will not listen to me now. If Rashek fails to lead the trek astray, then I have instructed the lad to kill Alendi. It is a distant hope. Alendi has survived assassins, wars, and catastrophes. And yet, I hope that in the frozen mountains of Terris, he may finally be exposed. I hope for a miracle. Alendi must not reach the Well of Ascension, for he must not be allowed to release the thing that is imprisoned there.";
        String[] alphas = new String[]{"lower", "upper", "numbers", "punctuation"};
        //String k = PolyCipher2.generateKey(plaintext, Cipher.getAlphabet(alphas));
        PolyCipher2 cipher = new PolyCipher2(key, alphas);
        //System.out.println(key);
        cipher.generateSquare();
        cipher.printSquare();
        cipher.scrambleSquare();
        cipher.printSquare();
        System.out.println("\n\n\n");
        cipher.storeBeta();
        
    }
}
