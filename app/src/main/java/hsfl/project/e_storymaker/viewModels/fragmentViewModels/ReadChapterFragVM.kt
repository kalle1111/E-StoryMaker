package hsfl.project.e_storymaker.viewModels.fragmentViewModels



import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter
import hsfl.project.e_storymaker.viewModels.ReadingVM
import java.lang.Exception

class ReadChapterFragVM : ReadingVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null

    private var chapterList: List<Chapter?>? = null
    private var currentChapter: Int = -1
    /*
    private var currentChapter: Chapter = Chapter("0", "0", "CHAPTER_TITLE",
        "\\n\\nLorem ipsum dolor sit amet, nam te nibh erant temporibus, ea veri efficiendi usu. Possim moderatius pri ad. Sea ea erat facete fierent, sed veri principes torquatos ad. Eos ne vitae volutpat, eum an utamur repudiandae. Duo munere accusata elaboraret cu, alia nostrum signiferumque nam eu.\\n\\nEam te ornatus detracto. Has ei fuisset rationibus sadipscing, probatus torquatos vituperatoribus et nam. Te pro inani scaevola adipisci. An sea bonorum volumus detracto, et posse nihil integre vix. Nam ad tota graecis, ancillae efficiantur mel id, diceret volumus dolores pri ut. Autem nonumy apeirian ne per, sea ei posse albucius.\\n\\nEst ne soleat maiestatis abhorreant. Omnium definiebas duo ne, mediocrem efficiantur sed eu. Qui eloquentiam philosophia an, pri te consul graeco dolorem, ornatus quaerendum ea mel. Cu eum sonet graece rationibus, nihil tincidunt ex vis. Autem dicat legere ex pri, fugit facilis imperdiet ex sea.\\n\\nBonorum scripserit id per. Eros ullamcorper eu eos. At omnes animal voluptua sit, et fugit appareat ius. Qui te fierent ancillae intellegat, eum petentium comprehensam ut. Tantas pericula te pri, alia sint id nec, pri ne mucius inermis scribentur. Ei corpora maluisset pri, per at facer tractatos vulputate, debitis cotidieque mea an. Id cum albucius eligendi consequuntur, nibh dolorum facilis no vix, dicam omittantur ius ne.\\n\\nEu pri persius pericula temporibus. Erant adversarium has ut, omnis munere graeci mel an. Epicuri petentium accommodare ius eu. Nam ut prima noluisse probatus, sumo option sea ei, ei omnis hendrerit eum.\\n\\nSea quod appellantur et. Sea cu denique sadipscing definitionem, audiam liberavisse philosophia mea cu. Possit aperiam molestie in eos, et augue aperiam deleniti mei. Possit propriae repudiandae quo eu.\\n\\nSaepe quidam referrentur ut his, eam ad case sonet admodum, ad interesset referrentur usu. Sit cu iudico labore convenire. Dicam verterem splendide duo at. Movet dissentias neglegentur ea est. Quaerendum omittantur dissentiunt cu vim, assum appellantur reformidans vix te, his expetenda conceptam posidonium ad. No duo hinc option accusam, id graeco neglegentur liberavisse vim, quo doctus sententiae ea.\\n\\nDicta ancillae postulant ex usu, soleat partiendo ne eam. Nam vidisse argumentum reformidans ad. Pro agam unum eu. Amet dicta sonet in cum, eros tation labore eu mel. Eum erant feugiat consectetuer id, ex graeco philosophia reprehendunt quo.\\n\\nEa perfecto tractatos voluptaria eam. Eos porro apeirian eloquentiam te, pri nisl purto evertitur ut, volumus sadipscing ut vis. Nam natum utinam ea, et iusto nullam noster usu, duo eu omnis utinam fabellas. Quot alterum at mei, ea molestie persequeris usu. Eum omnis solum inani ad. Iuvaret nostrum ut mei.\\n\\nPro ad volumus appetere iudicabit, ne autem dolor quaerendum sed. Duo ex illud aliquam appareat. Duo epicurei sententiae repudiandae ne. An quod vulputate pro, mazim malorum nec et, vim cu placerat dissentiet."
        , 0, 0)*/

    fun setApplicationContext(application: Application, storyID: String){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!

        chapterList = storyRep?.getAllChaptersOfStory(storyID)
        nextChapter()
        Log.i("ReadChapterFRAGVM", chapterList.toString())
    }

    fun nextChapter(): Boolean{
        return if (currentChapter+1 < chapterList?.count()!!){
            currentChapter++
            true
        }else{
            false
        }
    }

    private fun getChapter(){
        if (storyRep != null){
            //currentChapter = storyRep?.
            //TODO("GET CHAPTER!")
        }else{
            //THROW ERROR
        }
    }

    fun chapterTitle(): String{
        try {
            return chapterList?.get(currentChapter)?.chapter_Title!!
        }catch (e: Exception){
            return "NO CHAPTER FOUND!"
        }

    }

    fun chapterContent(): String{
        try {
            return chapterList?.get(currentChapter)?.content!!
        }catch (e: Exception){
            return "NO CHAPTER FOUND!"
        }
    }

}