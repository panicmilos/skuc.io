import { FC, useState } from "react";
import { createUseStyles } from "react-jss";
import { Button, Container, Table, TableBody, TableHead, TableRow, TextInput } from "../../imports";
import { StatusConfiguration } from "../../models";

type Props = {
  statusConfiguration: StatusConfiguration,
  onChange: (sc: StatusConfiguration) => any
}

const useStyles = createUseStyles(() => ({
  button: {
    alignSelf: 'center',
    padding: '0.25em',
    flexGrow: 0
  },
  container: 'margin-bottom: 0.5em'
}));

export const StatusConfigurationForm: FC<Props> = ({ statusConfiguration, onChange }) => {

  const [localStatusConfiguration, setLocalStatusConfiguration] = useState(statusConfiguration);

  const [key, setKey] = useState('');
  const [expectedValue, setExpectedValue] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const classes = useStyles();

  const handleAdd = () => {
    const existingThresholds = Object.keys(statusConfiguration);
    if (existingThresholds.includes(key)) {
      setErrorMessage('Threshold already exists.');
      return;
    }

    if (!expectedValue) {
      setErrorMessage('Expected value must be provided.');
      return;
    }

    localStatusConfiguration[key] = {
      expectedValue
    };

    setLocalStatusConfiguration({ ...localStatusConfiguration });
    onChange({ ...localStatusConfiguration });

    resetState();
  };

  const resetState = () => {
    setKey('');
    setExpectedValue('');
    setErrorMessage('');
  }

  const removeThreshold = (key: string) => {
    delete localStatusConfiguration[key];

    setLocalStatusConfiguration({ ...localStatusConfiguration });
    onChange({ ...localStatusConfiguration });
  }
  
  return (
    <>
      <Container className={classes.container}>

        <TextInput label="Key" value={key} onChange={setKey} />
        <TextInput label="Expected value" value={expectedValue} onChange={setExpectedValue} />
        <Button className={classes.button} onClick={handleAdd}>Add</Button>
      </ Container>
      {errorMessage}

      <Table hasPagination={false}>
        <TableHead columns={['Key', 'Expected value', 'Action']}/>
        <TableBody>
        {
          Object.keys(localStatusConfiguration)?.map((thresholdKey, i) =>
            {
              const thresholdValues = (localStatusConfiguration as any)[thresholdKey];
              return <TableRow
                        key={i}          
                        cells={[
                          thresholdKey,
                          thresholdValues.expectedValue,
                          <Button type="button" onClick={() => removeThreshold(thresholdKey)}>Remove</Button>
                        ]}
                      />
            }
          )
        }
        </TableBody>
      </Table>
    </>
  )
}