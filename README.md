# Darwin World project

Made by Łukasz Klon and Jakub Ciszewski for Objective Programming course at AGH UST.

### How to run in Intelij:
1. Click File->New->Project from Version Control...
2. In Repository URL in URL text field paste this link: `https://github.com/domino644/ciszewski_klon_piekielnie_dobry_projekt`
3. In directory field put path of your choice.
4. Hit `Clone`
5. After cloning is done Intelij should notify you in the bottom right corner that Gradle script has been detected, if so click load on the notification
6. Wait a second
7. Go to ProjekPo/src/main/java/WorldGUI
8. You should see that there is no SDK setup for this project and option to set it up - click it.
9. Chose any JDK 17.
10. After that you should be good to go. Click green triangle next to `main` method.
11. New windom should appear on your screen. Thats our app. Enjoy!

### Important info:
In order to configurations and statistics read/save work you have to work from folder that is parent of `ProjektPo`. 
```
parent_folder --|
                |->ProjekPo
```

In the example above you should be working in `parent_folder` ***NOT*** in `ProjekPo`.

Our app is not well-optimised so we recommend not to be to heavy and the parameters such as map dimensions or number of animals.

And yes it is `ProjekPo` not `ProjektPo`.
