# Core Concepts

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: the 2D array (in Blackjack.java) represents a part of the game
  state. The 2D array contains instances of the Card class, where each card
  represents one of the 52 standard playing cards. The 2D array has 10 rows
  and has the same number of columns as the number of players plus 1 for
  the dealer. It has 11 rows because it is not possible to not have a
  blackjack or bust after getting 11 cards. Since it is an array of objects,
  all the elements are initially null. This is important because null elements
  are skipped when calculating the totals of each player.

  A 2D array here is necessary because, for each player, we must keep track of
  the player's cards. This motivates the use of a 2D array.

  While there are some other arrays in Blackjack.java, it would not be
  sensible to store them as part of the 2D array for two reasons. 1) some
  of the arrays have a different size and 2) the other arrays only store
  ints, not Card instances. It would overcomplicate things to make a 2D
  array of objects containing this heterogeneous information. It would also
  be bad Java programming practice.

  2. Collections or Maps: the Java Collection (in Blackjack.java) represents
  a deck of cards. As cards are dealt to players/the dealer, these cards are
  removed from the remaining deck of cards. One major reason a Java Collection
  is used is because it gives access to powerful methods such as
  Collections.shuffle which effectively shuffled the deck of cards. Since order
  is important in the deck of cards (the order in which elements are retreived
  and removed from the deck matters), I opted to use an ArrayList. As the round
  goes on, cards are taken from the deck and put into the player's or dealer's
  hand. For this reason, the ArrayList used is central to modelling a part of
  the game state.

  3. File I/O: after any action which changes the game state occurs, the program
  writes the game state (the instance of the GameBoard class) into a file called
  state.bin. This is a binary file. It is written to the file using
  the writeObject() method called on an instance of ObjectOutputStream. This
  can be done because the GameBoard, Blackjack, and Card classes all implement
  Serializable. When a new game begins, the state.bin file is read in an
  analogous manner to how it is written. This allows users to pick up
  exactly where they left off—the program stores and retreives the entire game
  state. Finally, issues such as missing or corrupted state.bin file are
  handled. Specifically, it creates a new game with four players where all
  players have 1000 money.

  4. JUnit Testable Component: his is used to test the game model, which is
  fully independent of the GUI. It extensively tests gameplay and also tests
  many edge cases.

# Your Implementation

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  The Blackjack class is the game model. Methods are called on instances of
  this class and information about the game state is taken from instances of
  this class. Ranks and Suits are enums with the rank/suit of a playing card.
  The Card classes utilizes these enums to represent cards. The Card class is
  used in a deck of cards which is instantiated in the Blackjack class. Next,
  the GameBoard class contains the entire game state and GUI including the
  status bar at the time. This class effectively connects the game model
  with the GUI. The RunBlackjack class actually creates the frames, buttons,
  etc. used in Java Swing when displaying the GUI. Finally, the Game class
  simply runs the game.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  One major stumbling block occurred when testing the server model. In Blackjack,
  there is an edge case that the dealer peaks at the second card when there is a
  possibility that it could immediately be a blackjack. The server model had many
  issues while implementing this because in order to check whether it is a blackjack,
  it has to pull more cards from the deck, which automatically causes the
  calculateTotal method to show the total from both cards instead of just one.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Overall, I think my design works well. There is a good separation of functionality
  and the private state is fully encapsulated. However, I would refactor my code if
  given the chance. One thing I would do is to standardize my indices when I refer
  to players. Right now, there are some cases where I have to use a lower index since
  there are certain arrays which contain information about only players and some which
  describe both players and the dealer.

# External Resources

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

Thanks to Google Code's vector-playing-cards for high resolution .PNG card images.
Thanks to hitorstand.net (http://www.hitorstand.net/strategy.php) for blackjack rules.
