# A Markdown File where all members of the team will notate what they did over the course of the week.

# Week 1 (Oct 7-13):
yuming7: part of story mockup(admin), most of UML(admin), half of crc card(admin), half of backlog/risk management(admin).  

leighanbaroro12: most of story mockup(admin), parts of UML(admin), half of crc card(admin), half of backlog/risk management(admin).  

Peter: Implemented first drafts of product backlog Story Points and Risk Levels. Implemented mock-ups of Campsites list view and Past Messages view of Campsite Owner version of app. Throughout the next week, I plan to edit the UI mock-up to ensure it is fluid and thorough. I plan to edit backlog elements to better reflect relative difficulties and story points, and I plan to implement some CRC cards.

Nick: all of the entrant UI storyboard. 

Mies: Did all the entrant backlog/risk management, started on the entrant CRC cards

Salix: Worked on Owner UI mockup

# Week 2 (Oct 14-20):

Mies: Finished up entrant CRC cards and helped make entrant storyboard

Peter: Updated Owner UI Mockup with more pages, Created and updated Owner CRC cards, Updated CRCs to include known attributes, updated backlog designating certain items to be completed by the halfway point, Created Owner Campsite Creation and Owner Messaging storyboards.  


Yuming: refined admin crc cards

Leighan: Refined the Admin UI Mockup, created admin storyboard sequence, helped designate certain admin stories to be completed by the halfway point

Salix: Worked on Owner storyboards

Nick: worked on Entrant storyboard sequence, and some minor modifications to UI.

# Week 3:

Mies: Doing campsiteSearch, entrant, lottery, lotteryList, lotteryDrawer and user, but they are barebones missing some functions because I am still missing some classes

Leighan: Doing admin class, admin home screen UI, implementing things related to message: classes, screens, etc.

Yuming: doing admin class, doing profile class, profile list class, admin profile screen, confirm delete dialog, browse and remove profile

Nick: making UI elements, such as initial start screen *lacks functionality*. made our "skeleton" for other UI elements, changed the colors to match our mockups.

Peter: Added Campsite and campsiteList classes. Added views for campsite that implement most campsite addition and campsite detail viewing elements (proper testing is still needed and images still need implementation)

Salix: Start implementing the lottery menus: view lotteries, add lottery (basic details)

# Week 4:
Yuming: delete and view campsite(admin)

Mies: Doing the view reservations pages, as well as made a date picker to make bookings. Redid the user storage, and made an entrant able to leave an active lottery. Made the campsite search index the firestore, and you can look at campsitedetails by clicking on the cards (now broken but used to be able to). Made tests for Entrant items, refined comments, made cards for active lotteries, won lotteries, lost lotteries, and active reservations, users can leave/dismiss these, but not actually book anything yet.

Peter: Implemented proper Navcontroller data passing to keep track of the Uuid of the logged in User, implemented OwnerMenu, CampsiteList, CampsiteDetail, and Campsiteinput screens. Verified proper input of campsite elements such as images, name, etc. (except for date, still figuring that out.) Verified input campsites go to firestore. (small bugs still present.)

Leighan: Implemented classes relating to messages and notifications, and also made screens related to it for admin view. Also did UI tests for it.

Nick: search bar with filters mostly finished - filter by dates, now working on account creation 

Salix: Added the basic implementation of the lottery screens and navigation
