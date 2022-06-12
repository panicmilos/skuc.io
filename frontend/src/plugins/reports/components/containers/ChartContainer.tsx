import { FC } from "react";
import { createUseStyles } from "react-jss";
import { Card } from "../../imports";

type Props = {
  title: string,
  buttons: any
}

const useStyles = createUseStyles({

  buttonsGroup: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});


export const ChartContainer: FC<Props> = ({ title, buttons, children }) => {
  
  const classes = useStyles();
  
  return (
    <Card
      title={title}
    >
      <div className={classes.buttonsGroup}>
        {
          buttons
        }
      </div>
      
      {
        children
      }
    </Card>
  );
}